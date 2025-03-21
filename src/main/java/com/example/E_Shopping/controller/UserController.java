package com.example.E_Shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.example.E_Shopping.model.LoginRequest;
import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.model.Users;
import com.example.E_Shopping.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> saveUser(@RequestBody Users user) {
        ResponseObject responseObject = userService.registerUser(user);
        if (responseObject.getStatus().equals("Failed"))
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);

        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> loginUser(@RequestBody LoginRequest loginRequest) {
        ResponseObject userFound = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (userFound.getStatus().equals("Failed")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userFound);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userFound);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> deleteUserById(@PathVariable Long id) {
        boolean exist = userService.existById(id);
        if (exist) {
            ResponseObject responseObject = userService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "Khong co nguoi dung = " + id + " de xoa", ""));
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    ResponseEntity<ResponseObject> updateUserById(@RequestBody Users newUser, @PathVariable Long userId) {
        ResponseObject responseObject = userService.updateUserById(newUser, userId);
        if ("Failed".equals(responseObject.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseObject);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ResponseObject> getUserById(@PathVariable Long id) {
        ResponseObject responseObject = userService.getUserById(id);
        if ("Failed".equals(responseObject.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

}
