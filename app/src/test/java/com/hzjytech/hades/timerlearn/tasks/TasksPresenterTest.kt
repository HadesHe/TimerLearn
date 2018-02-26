package com.hzjytech.hades.timerlearn.tasks

import com.hzjytech.hades.timerlearn.data.Task
import com.hzjytech.hades.timerlearn.data.source.TasksRepository
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Created by zhanghehe on 2018/2/26.
 */
class TasksPresenterTest{
    @Mock private lateinit var tasksRepository: TasksRepository
    @Mock private lateinit var tasksView:TasksContract.View

    private lateinit var tasksPresenter: TasksPresenter

    private lateinit var tasks: MutableList<Task>

    @Before fun setupTasksPresenter(){
        MockitoAnnotations.initMocks(this)

        tasksPresenter=TasksPresenter(tasksRepository,tasksView)

        `when`(tasksView.isActive).thenReturn(true)

        tasks=Arrays.asList(
                Task("Title1","Description1"),
                Task("Title2","Description2").apply {
                    isCompleted=true
                },
                Task("Title3","Description3").apply {
                    isCompleted=true
                }
        )


    }
}