package com.myspringboot.journalApp.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myspringboot.journalApp.Entity.User;
import com.myspringboot.journalApp.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.getAll();
        if(users != null){
            return ResponseEntity.ok().body(users);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId myId){
        User checkUser = userService.getById(myId);
        if( checkUser != null ){
            return ResponseEntity.ok().body(checkUser);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User myUser){
        User checkUser = userService.saveUser(myUser);
        if( checkUser != null ){
            return ResponseEntity.ok().body(checkUser);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<String> deleteUserById(@PathVariable ObjectId myId){
        User checkUser = userService.deleteById(myId);
        if( checkUser != null ){
            return ResponseEntity.ok().body("User Deleted Succesfully");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
    }

    @PutMapping("/{userName}")
    public ResponseEntity<String> upadteUser(@RequestBody User myUser, @PathVariable String userName){
        User checkUser = userService.findByUserName(userName);
        if(checkUser != null){
            checkUser.setUserName(myUser.getUserName());
            checkUser.setPassword(myUser.getPassword());
            userService.saveUser(checkUser);
            return ResponseEntity.ok().body("User details update successfully");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
    }
}
