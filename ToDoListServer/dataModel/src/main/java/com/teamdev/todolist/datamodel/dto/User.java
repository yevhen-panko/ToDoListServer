package com.teamdev.todolist.datamodel.dto;

import com.teamdev.todolist.UserAuthority;
import com.teamdev.todolist.datamodel.entity.UserEntity;

public class User {

    public User() {
    }

    public User(Long id, String username, String password, UserAuthority authority, Boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.enabled = enabled;
    }

    public User(UserEntity userEntity){
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.authority = userEntity.getAuthority();
        this.enabled = userEntity.getEnabled();
    }

    private Long id;
    private String username;
    private String password;
    private UserAuthority authority;
    private Boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(UserAuthority authority) {
        this.authority = authority;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
