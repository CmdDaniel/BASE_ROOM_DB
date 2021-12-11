package com.noice.base_room_db.UI.task_list_fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.noice.base_room_db.DATA.room_setup.SortColumn
import com.noice.base_room_db.R
import com.noice.base_room_db.databinding.FragmentTaskListMasterBinding
import com.noice.base_room_db.UI.task_list_fragment.task_list_adapter.TasksListRVAdapter
import com.noice.base_room_db.UI.task_list_fragment.task_list_view_model.TaskListViewModel


class TaskListFragment : Fragment() {

    private val columnCount = 1
    lateinit var bind: FragmentTaskListMasterBinding
    lateinit var vm:TaskListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = ViewModelProvider(this)[TaskListViewModel::class.java]
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bind = FragmentTaskListMasterBinding.inflate(layoutInflater, container, false)

        bind.list.apply {

            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = TasksListRVAdapter().also {
                it.setOnItemClick(object : TasksListRVAdapter.onTaskClick {
                    override fun noiceClickImplement(taskId: Long) {
                        findNavController().navigate(
                            TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(
                                taskId
                            )
                        )
                    }

                })
            }

//            vm.sortedTasks.observe(viewLifecycleOwner) {
//                (adapter as TasksListRVAdapter).submitList(it)
//            }

            vm.tasks.observe(viewLifecycleOwner) {
                (adapter as TasksListRVAdapter).submitList(it)
                Log.i("TLF","tasks list changed")
            }
        }

        bind.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(0)
            )
        }
        return bind.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            R.id.priority_sort->{
                item.isChecked = true
                vm.changeSortOrder(SortColumn.Priority)
                true
            }
            R.id.title_sort->{
                item.isChecked = true
                vm.changeSortOrder(SortColumn.Title)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}