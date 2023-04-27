package ru.kata.spring.boot_security.demo.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.Mapper;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;

    private final Mapper mapper;

    private AdminRestController(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/viewUser")
    public ResponseEntity<User> showUser(Principal principal) {
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }


    @PostMapping("/users")
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(mapper.convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> show(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getByUserId(id));
    }

    @PutMapping("/users")
    public ResponseEntity<HttpStatus> update(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
