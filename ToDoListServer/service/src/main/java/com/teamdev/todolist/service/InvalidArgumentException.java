package com.teamdev.todolist.service;

public class InvalidArgumentException extends Exception{

    public InvalidArgumentException(String message){
        super(message);
    }
}
