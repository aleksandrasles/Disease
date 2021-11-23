package com.application.disease.controller;

import com.application.disease.dao.UserRepository;
import com.application.disease.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/admin")
@RestController
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id){
        if(id == null ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userRepository.findUserById(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userRepository.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userRepository.findAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") String id){
        if(id == null ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userRepository.findUserById(id) == null || user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        return new ResponseEntity<>(userRepository.updateUser(user),HttpStatus.OK);
    }

    @PostMapping("/users/new")
    public ResponseEntity<User> registerNewUser(@RequestBody User user){
        user.setId(UUID.randomUUID().toString());
        userRepository.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") String id){
        userRepository.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
