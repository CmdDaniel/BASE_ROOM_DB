package com.noice.base_room_db.DATA.room_setup

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskDB: RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var instance: TaskDB? = null

        fun getInstance(context: Context) =

            instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TaskDB::class.java,
                    "task_db"
                ).build()
            }
        }
    }
