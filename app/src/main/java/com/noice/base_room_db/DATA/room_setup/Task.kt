package com.noice.base_room_db.DATA.room_setup

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) var id : Long,
                val title: String,
                val desc:String,
                @ColumnInfo(name = "priority") val priority: Int,
                val status: Int)

enum class SortColumn{
    Title,
    Priority
}

enum class TaskStatus{
    Open,
    Closed
}

enum class PriorityLevel{
    Low,
    Medium,
    High
}
