package com.edu.mum.service.impl;

import com.edu.mum.domain.User;
import com.edu.mum.repository.UserRepository;
import com.edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User authenticate(String uname, String pword) {
        return userRepository.findDistinctByUserNameAndPassword(uname, pword);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void add(User user) {
        userRepository.save(user);
    }

    public User get(Long id) {
        return userRepository.findById(id).get();
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
