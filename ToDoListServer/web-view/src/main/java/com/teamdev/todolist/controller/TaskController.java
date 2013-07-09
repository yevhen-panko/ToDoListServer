package com.teamdev.todolist.controller;

import com.teamdev.todolist.TaskState;
import com.teamdev.todolist.datamodel.dto.Task;
import com.teamdev.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public Task createTask(@RequestBody final Task task){
        return taskService.createTask(task);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public Boolean deleteTask(@RequestBody final Task task){
        return taskService.deleteTask(task);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ResponseBody
    public Boolean updateTask(@RequestBody final Task task){
        return taskService.updateTask(task);
    }

    @RequestMapping(value = "/author/{authorID}/{state}", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Task> findTasksByAuthorAndState(@PathVariable Long authorID, @PathVariable TaskState state){
        return taskService.findTasksByAuthorAndState(authorID, state);
    }

    @RequestMapping(value = "/assignee/{assigneeID}/{state}", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Task> findTasksByAssigneeAndState(@PathVariable Long assigneeID, @PathVariable TaskState state){
        return taskService.findTasksByAssigneeAndState(assigneeID, state);
    }
}
