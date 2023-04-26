package ru.kata.spring.boot_security.demo.controller;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public AdminApiController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/viewUser")
    public ResponseEntity<User> showUser(Principal principal) {
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.allUsers();
    }


    @PostMapping("/users")
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public User show(@PathVariable("id") Long id) {
        User user = userService.getByUserId(id);
        if(user==null){
            throw new UserNotFoundException("User с ID " + id + " в базе данных отсутствует");
        }

        return user;
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) {
        userService.update(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
