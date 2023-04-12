package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class Init {

    private final UserService userService;


    @Autowired
    public Init(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void initializeDB() {
        User user = new User("admin", "admin", "admin@mail.ru", 25, "admin", Set.of(new Role("ROLE_ADMIN")));
        User user1 = new User("user", "user", "user@mail.ru", 34, "user", Set.of(new Role("ROLE_USER")));
        userService.saveUser(user);
        userService.saveUser(user1);
    }

}
