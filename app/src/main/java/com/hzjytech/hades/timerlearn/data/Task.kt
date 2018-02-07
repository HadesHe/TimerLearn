package com.hzjytech.hades.timerlearn.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by zhanghehe on 2018/1/8.
 */

@Entity(tableName = "tasks")
data class Task (
        @ColumnInfo(name = "title") var title: String ,
        @ColumnInfo(name = "description") var description: String,
        @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString()
) {
    @ColumnInfo(name = "completed") var isCompleted = false

    val titleForList: String
        get() = if (title.isNotEmpty()) title else description
    val isActive
        get() = !isCompleted
    val isEmpty
        get() = title.isEmpty() && description.isEmpty()

}