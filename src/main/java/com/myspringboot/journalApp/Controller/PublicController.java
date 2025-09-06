package com.myspringboot.journalApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myspringboot.journalApp.Entity.User;
import com.myspringboot.journalApp.Service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/check")
    public String healthCheck(){
        return "Ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User myUser){
        User checkUser = userService.saveNewUser(myUser);
        if( checkUser != null ){
            return ResponseEntity.ok().body(checkUser);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }
}
