package com.teamdev.todolist.controller;

import com.teamdev.todolist.datamodel.dto.User;
import com.teamdev.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public User createUser(@RequestBody final User user){
        return userService.createUser(user.getUsername(), user.getPassword(), user.getAuthority(), user.getEnabled());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Boolean updateUser(@RequestBody final User user){
        return userService.updateUser(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Collection<User> findAllUsers(){
        return userService.findAllUsers();
    }

    @RequestMapping(value = "/id/{userID}", method = RequestMethod.GET)
    @ResponseBody
    public User findUserByID(@PathVariable Long userID){
        return userService.findUserByID(userID);
    }

    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    @ResponseBody
    public User findCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login;
        if (principal instanceof UserDetails) {
            login = ((UserDetails)principal).getUsername();
        } else {
            login = principal.toString();
        }
        return userService.findUserByUsername(login);
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET, params = "username")
    @ResponseBody
    public User findUserByUsername(@RequestParam String username){
        return userService.findUserByUsername(username);
    }
}
