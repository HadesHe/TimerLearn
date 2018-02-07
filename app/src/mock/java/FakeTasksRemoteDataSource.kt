import android.support.annotation.VisibleForTesting
import com.hzjytech.hades.timerlearn.data.Task
import com.hzjytech.hades.timerlearn.data.source.TasksDataSource
import java.util.*

/**
 * Created by zhanghehe on 2018/2/4.
 */
class FakeTasksRemoteDataSource private constructor() : TasksDataSource {

    private val TASKS_SERVICE_DATA = LinkedHashMap<String, Task>()

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        callback.onTasksLoaded(ArrayList(TASKS_SERVICE_DATA.values));
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        val task = TASKS_SERVICE_DATA[taskId]
        if (task != null) {
            callback.onTaskLoaded(task)
        } else {
            callback.onDataNotAvailable()
        }
    }

    override fun saveTask(task: Task) {
        TASKS_SERVICE_DATA.put(task.id, task)
    }

    override fun completeTask(task: Task) {
        val completedTask = Task(task.title, task.description, task.id).apply {
            isCompleted = true
        }
        TASKS_SERVICE_DATA.put(task.id, completedTask)
    }

    override fun completeTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateTask(task: Task) {
        val activeTask = Task(task.title, task.description, task.id)
        TASKS_SERVICE_DATA.put(task.id, activeTask)
    }

    override fun activateTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearCompletedTasks() {
        with(
                TASKS_SERVICE_DATA.entries.iterator()
        ) {
            while (hasNext()){
                if(next().value.isCompleted){
                    remove()
                }
            }
        }
    }

    override fun refreshTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllTasks() {
        TASKS_SERVICE_DATA.clear()
    }

    override fun deleteTask(taskId: String) {
        TASKS_SERVICE_DATA.remove(taskId)
    }

    @VisibleForTesting
    fun addTasks(vararg  tasks: Task){
        for(task in tasks){
            TASKS_SERVICE_DATA.put(task.id,task)
        }
    }

    companion object {
        private lateinit var INSTANCE:FakeTasksRemoteDataSource
        private var needNewInstance=true

        fun getInstance():FakeTasksRemoteDataSource{
            if(needNewInstance){
                INSTANCE=FakeTasksRemoteDataSource()
                needNewInstance=false
            }
            return INSTANCE
        }
    }

}