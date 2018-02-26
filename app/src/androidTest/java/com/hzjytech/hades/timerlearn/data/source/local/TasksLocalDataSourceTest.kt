package com.hzjytech.hades.timerlearn.data.source.local

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.hzjytech.hades.timerlearn.data.Task
import com.hzjytech.hades.timerlearn.data.source.TasksDataSource
import com.hzjytech.hades.timerlearn.utils.SingleExecutors
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import java.util.*

/**
 * Created by zhanghehe on 2018/2/11.
 */
@RunWith(AndroidJUnit4::class) @LargeTest class TasksLocalDataSourceTest{

    private val TITLE="title"
    private val TITLE2="title2"
    private val TITLE3="title3"

    private lateinit var database: ToDoDataBase

    private lateinit var localDataSource: TasksLocalDataSource

    @Before
    fun setup(){
        database=Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                ToDoDataBase::class.java).build()

        TasksLocalDataSource.clearInstance()
        localDataSource=TasksLocalDataSource.getInstance(SingleExecutors(),
                database.taskDao())

    }

    @After
    fun cleanUp(){
        database.close()
        TasksLocalDataSource.clearInstance()
    }

    @Test fun testPreConditions(){
        assertNotNull(localDataSource)
    }

    @Test fun saveTask_retrievesTask(){
        val newTask=Task(TITLE,"")
        with(localDataSource){
            saveTask(newTask)

            getTask(newTask.id,object:TasksDataSource.GetTaskCallback{
                override fun onTaskLoaded(task: Task) {
                    assertThat(task,`is`(newTask))
                }

                override fun onDataNotAvailable() {
                    fail("Callback error")
                }

            })
        }

    }

    @Test fun completeTask_retrievedTaskIsCompleted(){
        val newTask=Task(TITLE,"")

        localDataSource.saveTask(newTask)
        localDataSource.completeTask(newTask)

        localDataSource.getTask(newTask.id,
                object :TasksDataSource.GetTaskCallback{
                    override fun onTaskLoaded(task: Task) {
                        assertThat(task,`is`(newTask))
                        assertThat(task.isCompleted,`is`(true))
                    }

                    override fun onDataNotAvailable() {
                        fail("Callback error")
                    }
                })
    }

    @Test fun activeTask_retrievedTaskIsActive(){
        val callback= mock(TasksDataSource.GetTaskCallback::class.java)
        val newTask=Task(TITLE,"")

        with(localDataSource){
            saveTask(newTask)
            completeTask(newTask)
            activateTask(newTask)
            getTask(newTask.id,callback)
        }
        verify(callback, never()).onDataNotAvailable()
        verify(callback).onTaskLoaded(newTask)

        assertThat(newTask.isCompleted,`is`(false))

    }

    @Test fun clearCompletedTask_taskNotRetrievable(){
        val callback1= mock(TasksDataSource.GetTaskCallback::class.java)
        val callback2= mock(TasksDataSource.GetTaskCallback::class.java)
        val callback3= mock(TasksDataSource.GetTaskCallback::class.java)

        val newTask1=Task(TITLE,"")
        val newTask2=Task(TITLE2,"")
        val newTask3=Task(TITLE3,"")

        with(localDataSource){
            saveTask(newTask1)
            completeTask(newTask1)
            saveTask(newTask2)
            completeTask(newTask2)
            saveTask(newTask3)
            clearCompletedTasks()

            getTask(newTask1.id,callback1)

            verify(callback1).onDataNotAvailable()
            verify(callback1, never()).onTaskLoaded(newTask1)

            getTask(newTask2.id,callback2)
            verify(callback2).onDataNotAvailable()
            verify(callback2, never()).onTaskLoaded(newTask2)

            getTask(newTask3.id,callback3)
            verify(callback3, never()).onDataNotAvailable()
            verify(callback3).onTaskLoaded(newTask3)

        }

    }

    @Test fun deleteAllTasks_emptyListOfRetrievedTask(){
        with(localDataSource){
            saveTask(Task(TITLE,""))
            val callback= mock(TasksDataSource.LoadTasksCallback::class.java)

            deleteAllTasks()
            getTasks(callback)
            verify(callback).onDataNotAvailable()
            verify(callback, never()).onTasksLoaded(LinkedList<Task>())
        }
    }

    @Test fun getTasks_retrieveSavedTasks(){

        with(localDataSource){
            val newTasks1=Task(TITLE,"")
            saveTask(newTasks1)
            val newTask2=Task(TITLE,"")
            saveTask(newTask2)

            getTasks(object :TasksDataSource.LoadTasksCallback{
                override fun onTasksLoaded(tasks: List<Task>) {
                    assertNotNull(tasks)
                    assertTrue(tasks.size >= 2)

                    var newTask1IdFound=false
                    var newTask2IdFound=false

                    for (task in tasks) {
                        if(task.id==newTasks1.id){
                            newTask1IdFound=true
                        }

                        if(task.id==newTask2.id){
                            newTask2IdFound=true
                        }
                    }

                    assertTrue(newTask1IdFound)
                    assertTrue(newTask2IdFound)
                }

                override fun onDataNotAvailable() {
                    fail()
                }

            })

        }
    }


}