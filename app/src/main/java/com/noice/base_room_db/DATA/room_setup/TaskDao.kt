package com.noice.base_room_db.DATA.room_setup

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("select * from tasks where status = :status order by priority desc")
    fun getTasksByPriority(status:Int): LiveData<List<Task>>

    @Query("select * from tasks where status = :status order by title asc")
    fun getTasksByTitle(status:Int): LiveData<List<Task>>

    @Query("select * from tasks where id > 0 order by priority")
    fun getAllTasks():LiveData<List<Task>>

    @Query("Select * from tasks where id = :id")
    fun getTask(id:Long): LiveData<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)


}