package com.san.juan.app.andalaosa.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.juan.app.andalaosa.data.DataRepository
import com.san.juan.app.andalaosa.data.data_model.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    private var _insertStatus = MutableLiveData<Boolean>()
    val insertStatus = _insertStatus

    private var _updateStatus = MutableLiveData<Boolean>()
    val updateStatus = _updateStatus

    private var _goalList = MutableLiveData<MutableList<Goal>>()
    val goalList = _goalList


    fun saveGoal(goal: Goal){
        viewModelScope.launch {
            try {
                val result = dataRepository.saveGoal(goal)
                if(result == null){
                    _insertStatus.value = true
                }
            }catch (e: Exception){
                _insertStatus.value = false
            }
        }
    }

    fun getAllGoals(){
        viewModelScope.launch {
            try {
                _goalList.value = dataRepository.getAllGoals()

            }catch (e: Exception){
                _goalList.value = mutableListOf()
            }
        }
    }

    fun updateGoalStatus(goal: Goal){
        viewModelScope.launch {
            try {
                val result = dataRepository.updateGoalStatus(goal)

                    _updateStatus.value = true

            }catch (e: Exception){
                _updateStatus.value = false
            }
        }
    }
}