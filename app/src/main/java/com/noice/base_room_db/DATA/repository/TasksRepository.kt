package com.noice.base_room_db.DATA.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.noice.base_room_db.DATA.room_setup.SortColumn
import com.noice.base_room_db.DATA.room_setup.TaskDB
import com.noice.base_room_db.DATA.room_setup.Task
import com.noice.base_room_db.DATA.room_setup.TaskStatus

class TasksRepository(context: Application) {

    private val taskDao = (TaskDB.getInstance(context)).taskDao()

    fun getTasksBySort(sortColumn: SortColumn = SortColumn.Priority):LiveData<List<Task>>{
        return when(sortColumn){
            SortColumn.Priority-> {
                taskDao.getTasksByPriority(TaskStatus.Open.ordinal)
            }
            else -> {
                taskDao.getTasksByTitle(TaskStatus.Open.ordinal)
            }
        }
    }

    fun getAllTasks() : LiveData<List<Task>> = taskDao.getAllTasks()


    fun getTask(id:Long):LiveData<Task>{
        return taskDao.getTask(id)
    }

    suspend fun insertTask(task: Task){
        taskDao.insertTask(task)
    }
    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }
    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

}