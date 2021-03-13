package com.san.juan.app.andalaosa.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.san.juan.app.andalaosa.data.data_model.Goal
import com.san.juan.app.andalaosa.data.local.dao.GoalsDAO

/**
 * @author Dario Carrizo on 13/3/2021
 **/
@Database(entities = [Goal::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "goal_table"
            ).build()
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

    abstract fun goalDao(): GoalsDAO
}