package com.teamdev.todolist.datamodel.entity;

import com.teamdev.todolist.UserAuthority;
import com.teamdev.todolist.datamodel.dto.User;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
        @NamedQuery(name = "User.findUserByUsername", query = "SELECT u FROM UserEntity u WHERE u.username = :username"),
        @NamedQuery(name = "User.findUserByID", query = "SELECT u FROM UserEntity u WHERE u.id = :id"),
        @NamedQuery(name = "User.findAllUsers", query = "SELECT u FROM UserEntity u")
})

@Entity
public class UserEntity implements Serializable {

    public UserEntity(){}

    public UserEntity(String username, String password, UserAuthority authority, Boolean enabled){
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.enabled = enabled;
    }

    public UserEntity(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authority = user.getAuthority();
        this.enabled = user.getEnabled();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
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

    public void setUsername(String name) {
        this.username = name;
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
        return "UserEntity{" +
                "id=" + id +
                ", login='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
