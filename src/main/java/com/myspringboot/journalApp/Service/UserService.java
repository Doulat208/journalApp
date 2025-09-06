package com.myspringboot.journalApp.Service;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myspringboot.journalApp.Entity.User;
import com.myspringboot.journalApp.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User saveUser(User user){
        // myEntry.setDate(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    public User saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User"));
        userRepository.save(user);
        return user;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }
    
    public User getById(ObjectId myId){
        return userRepository.findById(myId).orElse(null);
    }

    public User updateEntry(){
        return null;
    }

    public User deleteById(ObjectId myId){
        User temp = userRepository.findById(myId).orElse(null);
        if(temp != null)
            userRepository.deleteById(myId);
        return temp;
    }

    public void deleteByUserName(String userName){
        userRepository.deleteByUserName(userName);
    }

    public User findByUserName(String myUser){
        return userRepository.findByUserName(myUser);
    }
}
