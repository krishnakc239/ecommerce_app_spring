package com.edu.mum.service;

import com.edu.mum.domain.User;

import java.util.List;

public interface UserService {
    public User authenticate(String uname, String pword);
    public List<User> getAll();
    public void add(User user);
    public User get(Long id);
    public void update(User user);
    public void delete(Long id);
}
