package com.noice.base_room_db.UI.task_list_fragment.task_list_view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.noice.base_room_db.DATA.repository.TasksRepository
import com.noice.base_room_db.DATA.room_setup.SortColumn
import com.noice.base_room_db.DATA.room_setup.Task
import com.noice.base_room_db.UI.task_list_fragment.task_list_adapter.TasksListRVAdapter

class TaskListViewModel(context:Application):AndroidViewModel(context) {
    private val repo = TasksRepository(getApplication())

    private val _sortOrder = MutableLiveData<SortColumn>(SortColumn.Priority)


    val isUpdated = MutableLiveData(1)

    var tasks = repo.getAllTasks()


    val sortedTasks = Transformations.switchMap(_sortOrder){
        repo.getTasksBySort(_sortOrder.value!!)
    }

    fun changeSortOrder(sortColumn: SortColumn){
        _sortOrder.value = sortColumn
    }

    fun updateTasksList(){
        isUpdated.value = 10
        Log.i("After UpdateTask()",tasks.value.toString())
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("TASK LIST FRAGMENT","This viewmodel dead")
    }
}