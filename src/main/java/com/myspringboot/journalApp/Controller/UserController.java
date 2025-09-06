package com.myspringboot.journalApp.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myspringboot.journalApp.Entity.User;
import com.myspringboot.journalApp.Repository.UserRepository;
import com.myspringboot.journalApp.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // @Autowired
    // private UserRepository userRepository;

    // @GetMapping
    // public ResponseEntity<List<User>> getAllUser(){
    //     List<User> users = userService.getAll();
    //     if(users != null){
    //         return ResponseEntity.ok().body(users);
    //     }else{
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @GetMapping("/id/{myId}")
    // public ResponseEntity<User> getUserById(@PathVariable ObjectId myId){
    //     User checkUser = userService.getById(myId);
    //     if( checkUser != null ){
    //         return ResponseEntity.ok().body(checkUser);
    //     }else{
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @PostMapping
    // public ResponseEntity<User> createUser(@RequestBody User myUser){
    //     User checkUser = userService.saveNewUser(myUser);
    //     if( checkUser != null ){
    //         return ResponseEntity.ok().body(checkUser);
    //     } else{
    //         return ResponseEntity.badRequest().build();
    //     }
    // }

    @DeleteMapping
    public ResponseEntity<String> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteByUserName(userName);
        return ResponseEntity.ok().body("User Deleted Succesfully");
    }

    @PutMapping
    public ResponseEntity<String> upadteUser(@RequestBody User myUser){
        // Thiss is how we gwt the userName of the authenticated user how is still login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User checkUser = userService.findByUserName(userName);
        checkUser.setUserName(myUser.getUserName());
        checkUser.setPassword(myUser.getPassword());
        userService.saveNewUser(checkUser);
        return ResponseEntity.ok().body("User details update successfully");
    }
}
