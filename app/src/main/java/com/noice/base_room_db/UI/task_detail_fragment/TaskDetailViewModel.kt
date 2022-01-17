package com.noice.base_room_db.UI.task_detail_fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.noice.base_room_db.DATA.repository.TasksRepository
import com.noice.base_room_db.DATA.room_setup.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailViewModel(context: Application):AndroidViewModel(context) {

    private val NullTaskID = 0L

    private val repo = TasksRepository(context)

    private val _taskID = MutableLiveData<Long>(NullTaskID)

    val taskID:LiveData<Long>
        get() = _taskID

    fun setTaskID(id:Long){
        _taskID.value = id
    }

    val task:LiveData<Task> = Transformations.switchMap(_taskID){
        repo.getTask(it)
    }

    fun saveTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            if(_taskID.value == NullTaskID){
                repo.insertTask(task)
            }
            else{
                repo.updateTask(task)
            }
        }
    }

    fun deleteTask(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteTask(task.value!!)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("TASK Detail FRAGMENT","This viewmodel dead")
    }

}