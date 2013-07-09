package com.teamdev.todolist.datamodel.dto;

import com.teamdev.todolist.TaskState;
import com.teamdev.todolist.datamodel.entity.TaskEntity;

public class Task {

    public Task() {
    }

    public Task(Long id, String message, long dateInMilliseconds, User author, User assignee, TaskState state) {
        this.id = id;
        this.message = message;
        this.dateInMilliseconds = dateInMilliseconds;
        this.author = author;
        this.assignee = assignee;
        this.state = state;
    }

    public Task (TaskEntity taskEntity){
        this.id = taskEntity.getId();
        this.message = taskEntity.getMessage();
        this.dateInMilliseconds = taskEntity.getDateInMilliseconds();
        this.author = new User(taskEntity.getAuthor());
        this.assignee = new User(taskEntity.getAssignee());
        this.state = taskEntity.getState();
    }

    private Long id;
    private String message;
    private long dateInMilliseconds;
    private User author;
    private User assignee;
    private TaskState state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDateInMilliseconds() {
        return dateInMilliseconds;
    }

    public void setDateInMilliseconds(long dateInMilliseconds) {
        this.dateInMilliseconds = dateInMilliseconds;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", dateInMilliseconds=" + dateInMilliseconds +
                ", author=" + author +
                ", assignee=" + assignee +
                ", state=" + state +
                '}';
    }
}
