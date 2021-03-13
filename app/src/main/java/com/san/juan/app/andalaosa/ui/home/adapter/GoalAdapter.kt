package com.san.juan.app.andalaosa.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.san.juan.app.andalaosa.core.BaseViewHolder
import com.san.juan.app.andalaosa.data.data_model.Goal
import com.san.juan.app.andalaosa.databinding.GoalItemBinding

/**
 * @author Dario Carrizo on 13/3/2021
 **/
class GoalAdapter(private val goalList: MutableList<Goal>, private val clickItem: OnGoalClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnGoalClickListener{
        fun onClick(item: Goal)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = GoalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is GoalViewHolder -> {
                holder.bind(goalList[position])
            }
        }
    }

    override fun getItemCount(): Int = goalList.size

    private inner class GoalViewHolder(val binding: GoalItemBinding): BaseViewHolder<Goal>(binding.root){
        override fun bind(item: Goal) {
            binding.goalName.text = item.goal_name
            binding.goalDate.text = item.goal_date.toString()

            if(!item.goal_status){
                binding.btnGoalComplete.setOnClickListener {
                    clickItem.onClick(item)
                }
            }else {
                binding.btnGoalComplete.visibility = View.GONE
                binding.btnGoalComplete2.visibility = View.VISIBLE
            }

        }
    }
}