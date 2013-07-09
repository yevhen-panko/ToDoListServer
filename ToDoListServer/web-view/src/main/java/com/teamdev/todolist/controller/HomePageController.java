package com.teamdev.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomePageController {

    //Welcome-page redirect
    @RequestMapping(method = RequestMethod.GET)
    public String redirectToHomePage(){
        return "index";
    }
}
