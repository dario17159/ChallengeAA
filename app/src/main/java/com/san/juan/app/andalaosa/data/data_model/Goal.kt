package com.san.juan.app.andalaosa.data.data_model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Dario Carrizo on 13/3/2021
 **/
@Entity
data class Goal(
    @PrimaryKey var id_goal: String = "",
    val goal_name: String ="",
    val goal_date: Int = 0,
    var goal_status: Boolean = false
)