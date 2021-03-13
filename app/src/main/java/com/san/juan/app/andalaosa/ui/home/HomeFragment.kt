package com.san.juan.app.andalaosa.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.san.juan.app.andalaosa.R
import com.san.juan.app.andalaosa.core.hideKeyboard
import com.san.juan.app.andalaosa.core.toast
import com.san.juan.app.andalaosa.data.data_model.Goal
import com.san.juan.app.andalaosa.databinding.HomeFragmentBinding
import com.san.juan.app.andalaosa.ui.home.adapter.GoalAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment), GoalAdapter.OnGoalClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: HomeFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)

        showProgress()
        viewModel.getAllGoals()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnAddGoal.setOnClickListener {
            hideKeyboard()
            if(validateInputs()){
                showProgress()
                saveGoal()
            }
        }

        viewModel.insertStatus.observe(viewLifecycleOwner, Observer {

            if(it){
                clearInputs()
                getAllGoals()
            }else {
                "Ocurrió un eror al agregar la meta".toast(requireContext())
            }
        })

        viewModel.goalList.observe(viewLifecycleOwner, Observer {
            populateRv(it)

        })

        viewModel.updateStatus.observe(viewLifecycleOwner, Observer {
            if(it){
                getAllGoals()
            }
        })
    }

    private fun validateInputs(): Boolean{

        if(binding.inputDate.text.toString().trim().isEmpty()){
            "Fecha no ingresada".toast(requireContext())
            return false
        }


        if(binding.inputGoal.text.toString().trim().isEmpty()){
            "Meta no ingresada".toast(requireContext())
            return false
        }

        return true
    }
    private fun saveGoal(){
        val goal = Goal(
            goal_name = binding.inputGoal.text.toString(),
            goal_date = binding.inputDate.text.toString().toInt()
        )
        viewModel.saveGoal(goal)
    }
    private fun clearInputs(){
        binding.inputGoal.text = null
        binding.inputDate.text = null
        binding.inputDate.requestFocus()
    }
    private fun getAllGoals(){
        viewModel.getAllGoals()
    }
    private fun populateRv(goalList: MutableList<Goal>){
        binding.rvGoals.adapter = GoalAdapter(goalList, this)
        hideProgress()
    }

    override fun onClick(item: Goal) {
        showProgress()
        item.goal_status = !item.goal_status
        viewModel.updateGoalStatus(item)
    }

    private fun showProgress(){
        binding.progress.visibility = View.VISIBLE
        binding.overview.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        binding.progress.visibility = View.GONE
        binding.overview.visibility = View.GONE
    }


}