package com.san.juan.app.andalaosa.data

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.san.juan.app.andalaosa.core.toast
import com.san.juan.app.andalaosa.data.data_model.Goal
import com.san.juan.app.andalaosa.data.local.AppDatabase
import kotlinx.coroutines.tasks.await

/**
 * @author Dario Carrizo on 13/3/2021
 **/
class DataRepository(private val context: Context) {
    companion object {
        private const val GOAL_COLLECTION = "goals"
    }

    val fireDb = FirebaseFirestore.getInstance()
    val goalCollection = fireDb.collection(GOAL_COLLECTION)
    private var connectivity: ConnectivityManager? = null
    private var info: NetworkInfo? = null

    private val appDatabase = AppDatabase.getDatabase(context)


    suspend fun saveGoal(goal: Goal): Void? {
       if(checkConnection()){
           val id = goalCollection.document().id
           goal.id_goal = id
           return goalCollection.document(id).set(goal).await()
       }
        throw Exception("NOT CONNECTION")
    }

    suspend fun getAllGoals(): MutableList<Goal> {

        val goalList = mutableListOf<Goal>()

        if (checkConnection()) {

            val result = goalCollection.get().await()
            for (document in result) {
                val goal = document.toObject(Goal::class.java)
                appDatabase.goalDao().insertGoal(goal)
                goalList.add(goal)
            }

        } else {
            return appDatabase.goalDao().getAllGoals()
        }

        return goalList
    }

    suspend fun updateGoalStatus(goal: Goal): Void? {
        val updateMap = mutableMapOf<String, Any>()
        updateMap["goal_status"] = goal.goal_status
        appDatabase.goalDao().updateGoal(goal)
        return goalCollection.document(goal.id_goal).update(updateMap).await()
    }

    private fun checkConnection(): Boolean {
        var connectionEnabled = false
        connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        if (connectivity != null) {
            info = connectivity!!.activeNetworkInfo

            if (info != null) {
                if (info!!.state == NetworkInfo.State.CONNECTED) {
                    connectionEnabled = true
                }
            }
        }
        return connectionEnabled
    }
}