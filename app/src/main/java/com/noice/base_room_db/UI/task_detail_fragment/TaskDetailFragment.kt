package com.noice.base_room_db.UI.task_detail_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.noice.base_room_db.R
import com.noice.base_room_db.databinding.FragmentTaskDetailBinding
import com.noice.base_room_db.DATA.room_setup.PriorityLevel
import com.noice.base_room_db.DATA.room_setup.Task
import com.noice.base_room_db.DATA.room_setup.TaskStatus
import com.noice.base_room_db.UI.task_detail_fragment.task_detail_view_model.TaskDetailViewModel


class TaskDetailFragment : Fragment() {

    lateinit var bind: FragmentTaskDetailBinding

    lateinit var vm : TaskDetailViewModel

    private val priorities = mutableListOf<String>()

    private var taskId :Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taskId = TaskDetailFragmentArgs.fromBundle(requireArguments()).id
        Log.i("Nani!?", taskId.toString())

        PriorityLevel.values().forEach { priorities.add(it.name) }



        vm = ViewModelProvider(this)[TaskDetailViewModel::class.java]

        vm.setTaskID(taskId)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        vm.task.observe(viewLifecycleOwner,{
            setData(it)
            Log.i("Nani!? data observed","$it")
        })

        bind = FragmentTaskDetailBinding.inflate(layoutInflater,container,false)

        val arrayAdapter = ArrayAdapter(requireActivity(),R.layout.support_simple_spinner_dropdown_item,priorities)
        bind.prioritySpinner.adapter = arrayAdapter

        bind.prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateTaskPriorityView(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        bind.saveBtn.setOnClickListener {
            saveTask()
        }

        bind.deleteBtn.setOnClickListener {
            if(taskId == 0L){
                activity?.onBackPressed()
            }
            else
            deleteTask()
        }

    }

    private fun setData(task: Task?){

        if(task == null){
            Log.i("inside setData","$task , $taskId")
            bind.deleteBtn.text = "Cancel"
        }else{
            Log.i("inside setData","$task , $taskId")
            bind.taskTitleEt.setText(task.title)
            bind.taskDescEt.setText(task.desc)
            bind.prioritySpinner.setSelection(task.priority)
            updateTaskPriorityView(task.priority)
            if(task.status == TaskStatus.Open.ordinal ){
                bind.statusOpenRb.isChecked = true
            }
            else{
                bind.statusClosedRb.isChecked = true
            }
        }


    }

    private fun saveTask(){

        val title = bind.taskTitleEt.text.toString()
        val desc = bind.taskDescEt.text.toString()
        val priority = bind.prioritySpinner.selectedItemPosition
        val checkedRadioButton =  bind.statusRbGroup.findViewById<RadioButton>(bind.statusRbGroup.checkedRadioButtonId)
        val status = when(checkedRadioButton.text ){
            TaskStatus.Open.name ->{
                TaskStatus.Open.ordinal
            }
            else ->{
                TaskStatus.Closed.ordinal
            }
        }

        val task = Task(vm.taskID.value!!,title,desc,priority,status)

        vm.saveTask(task)

        activity?.onBackPressed()
    }

    private fun deleteTask(){

        vm.deleteTask()

        activity?.onBackPressed()
    }

    private fun updateTaskPriorityView(priority:Int){

        when(priority){

            PriorityLevel.High.ordinal ->{
                bind.priorityView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.color_high
                    )
                )
            }

            PriorityLevel.Medium.ordinal ->{
                bind.priorityView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.color_medium
                    )
                )
            }

            else-> {
                bind.priorityView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.color_low
                    )
                )
            }
        }
    }
}






















