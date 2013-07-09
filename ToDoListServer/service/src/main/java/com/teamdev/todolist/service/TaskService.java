package com.teamdev.todolist.service;

import com.teamdev.todolist.TaskState;
import com.teamdev.todolist.datamodel.entity.TaskEntity;
import com.teamdev.todolist.datamodel.entity.UserEntity;
import com.teamdev.todolist.datamodel.dto.Task;
import com.teamdev.todolist.datamodel.dto.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskService {

    @PersistenceContext
    private EntityManager em;

    private Query query;
    private Utils utils = new Utils();

    public Task createTask(String message, long dateInMilliseconds, User author, User assignee, TaskState state) {
        try {
            if (utils.checkTaskFields(message, dateInMilliseconds, author, assignee, state)){
                UserEntity authorEntity = em.find(UserEntity.class, author.getId());
                UserEntity assigneeEntity = em.find(UserEntity.class, assignee.getId());
                TaskEntity taskEntity = new TaskEntity(message, dateInMilliseconds, authorEntity, assigneeEntity, state);
                em.persist(taskEntity);
                return new Task(taskEntity);
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Task createTask(Task task) {
        try {
            if (utils.checkTaskFields(task.getMessage(), task.getDateInMilliseconds(), task.getAuthor(),
                    task.getAssignee(), task.getState())){
                UserEntity authorEntity = em.find(UserEntity.class, task.getAuthor().getId());
                UserEntity assigneeEntity = em.find(UserEntity.class, task.getAssignee().getId());
                TaskEntity taskEntity = new TaskEntity(task.getMessage(), task.getDateInMilliseconds(),
                        authorEntity, assigneeEntity, task.getState());
                em.persist(taskEntity);
                return new Task(taskEntity);
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean updateTask(Task task) {
        try {
            if (utils.checkTaskFields(task.getMessage(), task.getDateInMilliseconds(), task.getAuthor(),
                    task.getAssignee(), task.getState())){
                TaskEntity taskEntity = em.find(TaskEntity.class, task.getId());
                UserEntity author = em.find(UserEntity.class, task.getAuthor().getId());
                UserEntity assignee = em.find(UserEntity.class, task.getAssignee().getId());
                taskEntity.setId(task.getId());
                taskEntity.setMessage(task.getMessage());
                taskEntity.setDateInMilliseconds(task.getDateInMilliseconds());
                taskEntity.setAuthor(author);
                taskEntity.setAssignee(assignee);
                taskEntity.setState(task.getState());
                return true;
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteTask(Task task) {
        try {
            if (utils.checkTaskFields(task.getMessage(), task.getDateInMilliseconds(), task.getAuthor(),
                    task.getAssignee(), task.getState())){
                em.remove(em.find(TaskEntity.class, task.getId()));
                return true;
            } else return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Task findTaskByID(Long id) {
        try {
            if (id >= 0){
                TaskEntity taskEntity = em.find(TaskEntity.class, id);
                return new Task(taskEntity);
            } else throw new InvalidArgumentException("ID is wrong.");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Task> findTasksByAssigneeAndState(Long assigneeID, TaskState state) {
        try {
            if (assigneeID < 0) throw new InvalidArgumentException("Assignee ID is wrong.");
            if (state == null) throw new InvalidArgumentException("State is wrong.");
            query = em.createNamedQuery("Task.findTasksByAssigneeAndState");
            query.setParameter("assigneeID", assigneeID);
            query.setParameter("state", state);
            List<TaskEntity> taskEntities = query.getResultList();
            return utils.createTaskListFromTaskEntityList(taskEntities);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Task>();
        }
    }

    public List<Task> findTasksByAuthorAndState(Long authorID, TaskState state) {
        try {
            if (authorID < 0) throw new InvalidArgumentException("Author ID is wrong.");
            if (state == null) throw new InvalidArgumentException("State is wrong.");
            query = em.createNamedQuery("Task.findTasksByAuthorAndState");
            query.setParameter("authorID", authorID);
            query.setParameter("state", state);
            List<TaskEntity> taskEntities = query.getResultList();
            return utils.createTaskListFromTaskEntityList(taskEntities);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Task>();
        }
    }
}
