package com.teamdev.todolist.datamodel.entity;

import com.teamdev.todolist.TaskState;
import com.teamdev.todolist.datamodel.dto.Task;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
        @NamedQuery(name = "Task.findTaskByID", query = "SELECT t FROM TaskEntity t WHERE t.id = :id"),
        @NamedQuery(name = "Task.findTasksByAssigneeAndState", query = "SELECT t FROM TaskEntity t " +
                "WHERE t.assignee.id = :assigneeID AND t.state = :state ORDER BY t.dateInMilliseconds DESC"),
        @NamedQuery(name = "Task.findTasksByAuthorAndState", query = "SELECT t FROM TaskEntity t " +
                "WHERE t.author.id = :authorID AND t.state = :state ORDER BY t.dateInMilliseconds DESC")
})

@Entity
public class TaskEntity implements Serializable {
    public TaskEntity(){}

    public TaskEntity(String message, Long dateInMilliseconds, UserEntity author, UserEntity assignee, TaskState state){
        this.message = message;
        this.dateInMilliseconds = dateInMilliseconds;
        this.author = author;
        this.assignee = assignee;
        this.state = state;
    }

    public TaskEntity(Task task){
        this.message = task.getMessage();
        this.dateInMilliseconds = task.getDateInMilliseconds();
        this.author = new UserEntity(task.getAuthor());
        this.assignee = new UserEntity(task.getAssignee());
        this.state = task.getState();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private long dateInMilliseconds;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    private UserEntity assignee;

    @Enumerated(EnumType.STRING)
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

    public Long getDateInMilliseconds() {
        return dateInMilliseconds;
    }

    public void setDateInMilliseconds(Long dateInMilliseconds) {
        this.dateInMilliseconds = dateInMilliseconds;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public UserEntity getAssignee() {
        return assignee;
    }

    public void setAssignee(UserEntity assignee) {
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
        return "TaskEntity{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", dateInMilliseconds=" + dateInMilliseconds +
                ", author=" + author +
                ", assignee=" + assignee +
                ", state=" + state +
                '}';
    }
}

