package com.edu.mum.util;

import com.edu.mum.domain.User;
import com.edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionUtils {

    @Autowired
    private  UserService userService;

    public  User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.findByUsername(username);
        User loggedInUser = user.isPresent()? user.get() :  new User();
        return loggedInUser;
    }
}
