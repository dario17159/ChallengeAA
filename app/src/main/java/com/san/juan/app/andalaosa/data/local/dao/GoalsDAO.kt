package com.san.juan.app.andalaosa.data.local.dao

import androidx.room.*
import com.san.juan.app.andalaosa.data.data_model.Goal

/**
 * @author Dario Carrizo on 13/3/2021
 **/
@Dao
interface GoalsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Query("SELECT * FROM Goal")
    suspend fun getAllGoals(): MutableList<Goal>

    @Update
    suspend fun updateGoal(goal: Goal)
}