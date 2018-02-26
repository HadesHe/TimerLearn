package com.hzjytech.hades.timerlearn.data.source.local

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import android.support.test.runner.AndroidJUnit4
import com.hzjytech.hades.timerlearn.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by zhanghehe on 2018/2/8.
 */
@RunWith(AndroidJUnit4::class) public class TasksDaoTest{


    private lateinit var database: ToDoDataBase

    @Before fun initDb(){
        database =Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                ToDoDataBase::class.java).build()
    }

    @After fun closeDb()=database.close()

    @Test fun insertTaskAndGetById(){
        database.taskDao().insertTask(DEFAULT_TASK)

        val loaded=database.taskDao().getTaskById(DEFAULT_TASK.id)


        assertTask(loaded, DEFAULT_ID, DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_IS_COMPLETED)
    }

    @Test fun insertTaskReplacesOnConflict(){
        database.taskDao().insertTask(DEFAULT_TASK)

        val newTask=Task(DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_ID).apply {
            isCompleted= NEW_IS_COMPLETED
        }

        database.taskDao().insertTask(newTask)

        val loaded=database.taskDao().getTaskById(DEFAULT_TASK.id)

        assertTask(loaded, DEFAULT_ID, DEFAULT_TITLE,DEFAULT_DESCRIPTION, NEW_IS_COMPLETED)
    }

    @Test fun insertTaskAndGetTasks(){
        database.taskDao().insertTask(DEFAULT_TASK)

        val tasks=database.taskDao().getTasks()
        assertThat(tasks.size, `is`(1))
        assertTask(tasks[0], DEFAULT_ID, DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_IS_COMPLETED)
    }

    @Test fun updateTaskAndGetById(){
        database.taskDao().insertTask(DEFAULT_TASK)

        val updatedTask=Task(NEW_TITLE, NEW_DESCRIPTION, DEFAULT_ID).apply {
            isCompleted= NEW_IS_COMPLETED
        }
        database.taskDao().updateTask(updatedTask)

        val loaded=database.taskDao().getTaskById(DEFAULT_ID)

        assertTask(loaded, DEFAULT_ID, NEW_TITLE, NEW_DESCRIPTION, NEW_IS_COMPLETED)
    }

    @Test fun updateCompletedAndGetById(){
        database.taskDao().insertTask(DEFAULT_TASK)

        database.taskDao().updateCompleted(DEFAULT_TASK.id,false)
         val loaded=database.taskDao().getTaskById(DEFAULT_ID)

        assertTask(loaded, DEFAULT_TASK.id, DEFAULT_TASK.title, DEFAULT_TASK.description,false)
    }

    @Test fun deleteTaskByIdAndGettingTasks(){
        database.taskDao().insertTask(DEFAULT_TASK)
        database.taskDao().deleteTaskById(DEFAULT_TASK.id)

        val tasks=database.taskDao().getTasks()
        assertThat(tasks.size,`is`(0));
    }

    @Test fun deleteTasksAndGettingTasks(){
        database.taskDao().insertTask(DEFAULT_TASK)

        database.taskDao().deleteTasks()

        val tasks=database.taskDao().getTasks()
        assertThat(tasks.size,`is`(0))
    }

    @Test fun deleteCompletedTasksAndGettingTask(){
        database.taskDao().insertTask(DEFAULT_TASK)

        database.taskDao().deleteCompletedTasks()

        val tasks=database.taskDao().getTasks()

        assertThat(tasks.size,`is`(0))
    }

    private fun assertTask(loaded: Task?, defaulT_ID: String, defaulT_TITLE: String, defaulT_DESCRIPTION: String, defaulT_IS_COMPLETED: Boolean) {

        assertThat(loaded as Task, notNullValue())
        assertThat(loaded.id,`is`(defaulT_ID))
        assertThat(loaded.title,`is`(defaulT_TITLE))
        assertThat(loaded.description,`is`(defaulT_DESCRIPTION))
        assertThat(loaded.isCompleted,`is`(defaulT_IS_COMPLETED))

    }

    companion object {
        private val DEFAULT_TITLE="title"
        private val DEFAULT_DESCRIPTION="description"
        private val DEFAULT_ID="id"
        private val DEFAULT_IS_COMPLETED=true
        private val DEFAULT_TASK=Task(DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_ID).apply {
            isCompleted= DEFAULT_IS_COMPLETED
        }

        private val NEW_TITLE="title2"
        private val NEW_DESCRIPTION="description2"
        private val NEW_IS_COMPLETED=true



    }


}