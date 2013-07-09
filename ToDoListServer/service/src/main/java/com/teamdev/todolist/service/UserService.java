package com.teamdev.todolist.service;

import com.teamdev.todolist.UserAuthority;
import com.teamdev.todolist.datamodel.entity.UserEntity;
import com.teamdev.todolist.datamodel.dto.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class UserService {

    @PersistenceContext
    private EntityManager em;

    private Query query;
    private Utils utils = new Utils();

    public User createUser(String username, String password, UserAuthority authority, Boolean enabled) {
        try{
            if (utils.checkUserFields(username, password, authority, enabled)){
                UserEntity userEntity = new UserEntity(username, password, authority, enabled);
                em.persist(userEntity);
                return new User(userEntity);
            } else return  null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean updateUser(User user){
        try {
            if (utils.checkUserFields(user.getUsername(), user.getPassword(), user.getAuthority(),
                    user.getEnabled())){
                UserEntity userEntity = em.find(UserEntity.class, user.getId());
                userEntity.setUsername(user.getUsername());
                userEntity.setPassword(user.getPassword());
                userEntity.setAuthority(user.getAuthority());
                userEntity.setEnabled(user.getEnabled());
                return true;
            } else return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteUser(User user){
        try {
            if (utils.checkUserFields(user.getUsername(), user.getPassword(), user.getAuthority(),
                    user.getEnabled())){
                em.remove(em.find(UserEntity.class, user.getId()));
                return true;
            } else return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public User findUserByUsername(String username) {
        try {
            if (utils.isUserNameCorrectEmail(username)){
                query = em.createNamedQuery("User.findUserByUsername");
                query.setParameter("username", username);
                User user;
                user = new User((UserEntity)query.getSingleResult());
                return user;
            } else throw new InvalidArgumentException("User name is wrong.");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public User findUserByID(Long id) {
        try {
            if (id >= 0) {
                return new User(em.find(UserEntity.class, id));
            } else throw new InvalidArgumentException("ID is wrong.");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<User> findAllUsers(){
        try {
            query = em.createNamedQuery("User.findAllUsers");
            List<UserEntity> userEntities = query.getResultList();
            Iterator iterator = userEntities.iterator();
            List<User> users =  new ArrayList<User>();
            while(iterator.hasNext()){
                users.add(new User((UserEntity)iterator.next()));
            }
            return users;
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<User>();
        }
    }
}

