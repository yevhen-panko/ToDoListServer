package com.teamdev.todolist.service;

import com.teamdev.todolist.TaskState;
import com.teamdev.todolist.UserAuthority;
import com.teamdev.todolist.datamodel.dto.Task;
import com.teamdev.todolist.datamodel.dto.User;
import com.teamdev.todolist.datamodel.entity.TaskEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public List<Task> createTaskListFromTaskEntityList(List<TaskEntity> taskEntities) {
        Iterator iterator = taskEntities.iterator();
        List<Task> tasks = new ArrayList<Task>();
        while (iterator.hasNext()) {
            tasks.add(new Task((TaskEntity) iterator.next()));
        }
        return tasks;
    }

    public Boolean checkTaskFields(String message, long dateInMilliseconds, User author, User assignee, TaskState state) throws InvalidArgumentException{
        if (message.isEmpty()) throw new InvalidArgumentException("Message is empty.");
        if (dateInMilliseconds <= 0) throw new InvalidArgumentException("Date is wrong.");
        if (author == null) throw new InvalidArgumentException("Author is wrong");
        if (assignee == null) throw new InvalidArgumentException("Assignee is wrong.");
        if (state == null) throw new InvalidArgumentException("State is wrong.");
        return true;
    }

    public Boolean isUserNameCorrectEmail(String username){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public Boolean checkUserFields(String username, String password, UserAuthority authority, Boolean enabled) throws InvalidArgumentException{
        if (!isUserNameCorrectEmail(username)) throw new InvalidArgumentException("User name is wrong.");
        if (password.isEmpty()) throw new InvalidArgumentException("User password is wrong.");
        if (authority == null) throw new InvalidArgumentException("User authority is wrong.");
        if (!enabled) throw new InvalidArgumentException("User is disabled.");
        return true;
    }
}
