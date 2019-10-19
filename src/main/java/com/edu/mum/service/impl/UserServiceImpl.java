package com.edu.mum.service.impl;

import com.edu.mum.domain.User;
import com.edu.mum.repository.RoleRepository;
import com.edu.mum.repository.UserRepository;
import com.edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;

    private static final String USER_ROLE = "ROLE_USER";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

//    @Autowired
//    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
////        this.passwordEncoder = passwordEncoder;
//    }

    public Optional<User> findByUsernameAndPassword(String email, String pass) {
        return userRepository.findByUserNameAndPassword(email, pass);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.getOne(id);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
    @Override
    public User save(User user) {
//        // Encode plaintext password
//        user.setPassword(user.getPassword());
//        user.setActive(1);
//        // Set Role to ROLE_USER/ROLE_ADMIN
//        int count = userRepository.findAll().size();
//        if (count < 1){
//            user.setRoles(roleRepository.findByRole(ADMIN_ROLE));
//        }else {
//            user.setRoles(roleRepository.findByRole(USER_ROLE));
//        }

        return userRepository.save(user);
    }

    @Override
    public Page<User> findAllUsers(int page) {
        return this.userRepository.findAll(new PageRequest(subtractPageByOne(page),3));
    }

    @Override
    public User edit(User user) {
        return this.userRepository.save(user);
    }
    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Page<User> findAllByFirstNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmail(String searchParameter, String searchParameter1, String searchParameter2, int page) {
        return userRepository.findAllByFirstNameContainingIgnoreCaseOrUserNameContainingIgnoreCaseOrEmail(searchParameter, searchParameter1, searchParameter2, new PageRequest(subtractPageByOne(page),5));
    }

    @Override
    public Page<User> findAllByOrderByFirstName(int page) {
        return userRepository.findAllByOrderByFirstName(new PageRequest(subtractPageByOne(page),5));
    }

    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }



}
