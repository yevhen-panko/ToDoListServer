package com.teamdev.todolist.service;

import com.teamdev.todolist.TaskState;
import com.teamdev.todolist.UserAuthority;
import com.teamdev.todolist.datamodel.dto.Task;
import com.teamdev.todolist.datamodel.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testApplicationContext.xml")
@Transactional
public class TaskServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Test
    public void createTaskWithoutMessage(){
        String message = "";
        long dateInMilliseconds = Calendar.getInstance().getTimeInMillis();
        User author = userService.createUser("author@todolist.com", "password", UserAuthority.ROLE_USER, true);
        User assignee = userService.createUser("assignee@todolist.com", "password", UserAuthority.ROLE_USER, true);
        TaskState state = TaskState.ACTIVE;
        assertNull("Task without message created.",
                taskService.createTask(message, dateInMilliseconds, author, assignee, state));
    }

    @Test
    public void createTaskWithoutDate(){
        String message = "Message";
        long dateInMilliseconds = 0;
        User author = userService.createUser("author@todolist.com", "password", UserAuthority.ROLE_USER, true);
        User assignee = userService.createUser("assignee@todolist.com", "password", UserAuthority.ROLE_USER, true);
        TaskState state = TaskState.ACTIVE;
        assertNull("Task without date created.",
                taskService.createTask(message, dateInMilliseconds, author, assignee, state));
    }

    @Test
    public void createTaskWithoutAuthor(){
        String message = "Message";
        long dateInMilliseconds = Calendar.getInstance().getTimeInMillis();
        User author = null;
        User assignee = userService.createUser("assignee@todolist.com", "password", UserAuthority.ROLE_USER, true);
        TaskState state = TaskState.ACTIVE;
        assertNull("Task without author created.",
                taskService.createTask(message, dateInMilliseconds, author, assignee, state));
    }

    @Test
    public void createTaskWithoutAssignee(){
        String message = "Message";
        long dateInMilliseconds = Calendar.getInstance().getTimeInMillis();
        User author = userService.createUser("author@todolist.com", "password", UserAuthority.ROLE_USER, true);
        User assignee = null;
        TaskState state = TaskState.ACTIVE;
        assertNull("Task without assignee created.",
                taskService.createTask(message, dateInMilliseconds, author, assignee, state));
    }

    @Test
    public void createTaskWithoutState(){
        String message = "Message";
        long dateInMilliseconds = Calendar.getInstance().getTimeInMillis();
        User author = userService.createUser("author@todolist.com", "password", UserAuthority.ROLE_USER, true);
        User assignee = userService.createUser("assignee@todolist.com", "password", UserAuthority.ROLE_USER, true);
        TaskState state = null;
        assertNull("Task without state created.",
                taskService.createTask(message, dateInMilliseconds, author, assignee, state));
    }

    @Test
    public void testCreatedTaskHasID() {
        User user = userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        Task task = taskService.createTask("New TaskEntity 1", Calendar.getInstance().getTimeInMillis(), user, user, TaskState.ACTIVE);
        assertNotNull("Created taskEntity hasn't id.", task.getId());
    }

    @Test
    public void testNotFoundTaskByID() {
        assertNull("Found uncreated task.", taskService.findTaskByID(9999l));
    }

    @Test
    public void testFindTaskByID() {
        User user = userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        Task task = taskService.createTask("New TaskEntity 1", Calendar.getInstance().getTimeInMillis(), user, user, TaskState.ACTIVE);
        assertNotNull("Not found userEntity by ID.", taskService.findTaskByID(task.getId()));
    }

    @Test
    public void testFindTasksByAssigneeAndState() {
        User user1 = userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        User user2 = userService.createUser("EddieVanHalen@gmail.com", "password", UserAuthority.ROLE_USER, true);
        taskService.createTask("New TaskEntity 1", Calendar.getInstance().getTimeInMillis(), user1, user1, TaskState.COMPLETED);
        taskService.createTask("New TaskEntity 2", Calendar.getInstance().getTimeInMillis(), user1, user2, TaskState.ACTIVE);
        List<Task> tasks = taskService.findTasksByAssigneeAndState(user1.getId(), TaskState.COMPLETED);
        assertEquals("Size of taskEntities collection, selected by Owner and State isn't true.", 1, tasks.size());
    }

    @Test
    public void testFindTasksByAuthorAndState() {
        User user1 = userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        User user2 = userService.createUser("EddieVanHalen@gmail.com", "password", UserAuthority.ROLE_USER, true);
        taskService.createTask("New TaskEntity 1", Calendar.getInstance().getTimeInMillis(), user1, user1, TaskState.COMPLETED);
        taskService.createTask("New TaskEntity 2", Calendar.getInstance().getTimeInMillis(), user1, user2, TaskState.ACTIVE);
        List<Task> tasks = taskService.findTasksByAuthorAndState(user1.getId(), TaskState.ACTIVE);
        assertEquals("Size of taskEntities collection, selected by Author and State isn't true.", 1, tasks.size());
    }

    @Test
    public void testUpdateTask() {
        User user = userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        Task task = taskService.createTask("New TaskEntity 1", Calendar.getInstance().getTimeInMillis(), user, user, TaskState.ACTIVE);
        task.setState(TaskState.COMPLETED);
        taskService.updateTask(task);
        assertEquals("TaskEntity state after update is wrong.", TaskState.COMPLETED, taskService.findTaskByID(task.getId()).getState());
    }

    @Test
    public void testDeleteTask() {
        User user = userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        Task task = taskService.createTask("New TaskEntity 1", Calendar.getInstance().getTimeInMillis(), user, user, TaskState.ACTIVE);
        taskService.deleteTask(task);
        assertNull("TaskEntity isn't deleted", taskService.findTaskByID(task.getId()));
    }
}
