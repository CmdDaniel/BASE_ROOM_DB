package com.noice.base_room_db.UI.task_list_fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.noice.base_room_db.DATA.repository.TasksRepository
import com.noice.base_room_db.DATA.room_setup.SortColumn

class TaskListViewModel(context:Application):AndroidViewModel(context) {
    private val repo = TasksRepository(getApplication())

    private val _sortOrder = MutableLiveData(SortColumn.Priority)



    val sortedTasks = Transformations.switchMap(_sortOrder){
        repo.getTasksBySort(_sortOrder.value!!)
    }

    fun changeSortOrder(sortColumn: SortColumn){
        _sortOrder.value = sortColumn
    }



    override fun onCleared() {
        super.onCleared()
        Log.i("TASK LIST FRAGMENT","This viewmodel dead")
    }
}