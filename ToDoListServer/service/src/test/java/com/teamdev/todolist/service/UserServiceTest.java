package com.teamdev.todolist.service;

import com.teamdev.todolist.UserAuthority;
import com.teamdev.todolist.datamodel.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testApplicationContext.xml")
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testUsernameIsCorrectEmail(){
        String username = "admin@todolist.com";
        String password = "password";
        UserAuthority authority = UserAuthority.ROLE_USER;
        Boolean enabled = true;
        assertNotNull("Username admin@todolist.com is wrong email.",
                userService.createUser(username, password, authority, enabled));
        username = "admin123@todolist.com";
        assertNotNull("Username admin123@todolist.com is wrong email.",
                userService.createUser(username, password, authority, enabled));
        username = "admin-new@todolist.com";
        assertNotNull("Username admin-new@todolist.com is wrong email.",
                userService.createUser(username, password, authority, enabled));
        username = "123-admin-new@todolist.com";
        assertNotNull("Username 123-admin-new@todolist.com is wrong email.",
                userService.createUser(username, password, authority, enabled));
    }

    @Test
    public void testUsernameIsWrongEmail(){
        String username = "admin@todolist";
        String password = "password";
        UserAuthority authority = UserAuthority.ROLE_USER;
        Boolean enabled = true;
        assertNull("Username admin@todolist is correct email.",
                userService.createUser(username, password, authority, enabled));
        username = "admin.todolist.com";
        assertNull("Username admin.todolist.com is correct email.",
                userService.createUser(username, password, authority, enabled));
        username = "admin";
        assertNull("Username admin is correct email.",
                userService.createUser(username, password, authority, enabled));
    }

    @Test
    public void testCreateUserWithoutPassword(){
        String username = "admin@todolist.com";
        String password = "";
        UserAuthority authority = UserAuthority.ROLE_USER;
        Boolean enabled = true;
        assertNull("User without password created.",
                userService.createUser(username, password, authority, enabled));
    }

    @Test
    public void createUserWithoutAuthority(){
        String username = "admin@todolist.com";
        String password = "password";
        UserAuthority authority = null;
        Boolean enabled = true;
        assertNull("User without authority created.",
                userService.createUser(username, password, authority, enabled));
    }

    @Test
    public void createUserWithoutEnabled(){
        String username = "admin@todolist.com";
        String password = "password";
        UserAuthority authority = UserAuthority.ROLE_USER;
        Boolean enabled = false;
        assertNull("User without enabled created.",
                userService.createUser(username, password, authority, enabled));
    }

    @Test
    public void testSavedPersonHasId() {
        User user = userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        assertNotNull(user.getId());
    }

    @Test
    public void testFindUserByLogin() {
        userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        assertNotNull("Not found user with login RitchieBlackmore@gmail.com", userService.findUserByUsername("RitchieBlackmore@gmail.com"));
    }

    @Test
    public void testFindUserByID() {
        User user = userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        assertNotNull("Not found userEntity by ID", userService.findUserByID(user.getId()));
    }

    @Test
    public void testNotFoundUpdatedUser() {
        User user = userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        user.setUsername("JohnBonJovi@gmail.com");
        userService.updateUser(user);
        assertNull("Found updated user.", userService.findUserByUsername("RitchieBlackmore@gmail.com"));
    }

    @Test
    public void testFindUpdatedUser() {
        User user = userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        user.setUsername("JohnBonJovi@gmail.com");
        userService.updateUser(user);
        assertNotNull("Not found userEntity with updated login JohnBonJovi@mail.com.", userService.findUserByUsername("JohnBonJovi@gmail.com"));
    }

    @Test
    public void testDeleteUser() {
        User user = userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        userService.deleteUser(user);
        assertNull("Found deleted user", userService.findUserByUsername(user.getUsername()));
    }

    @Test
    public void testFindAllUsers() {
        userService.createUser("RitchieBlackmore@gmail.com", "password", UserAuthority.ROLE_USER, true);
        userService.createUser("EddieVanHalen@gmail.com", "password", UserAuthority.ROLE_USER, true);
        userService.createUser("JonBonJovi@gmail.com", "password", UserAuthority.ROLE_USER, true);
        assertEquals("Number of found users is wrong.", 3, userService.findAllUsers().size());
    }
}
