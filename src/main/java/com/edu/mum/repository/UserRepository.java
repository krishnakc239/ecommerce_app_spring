package com.edu.mum.repository;

import com.edu.mum.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);
    Optional<User> findByEmail(String email);

    Optional<User> findByUserNameAndPassword(String name, String pass);
    Page<User> findAllByOrderByFirstName(Pageable pageable);

    Page<User> findAllByFirstNameContainingIgnoreCaseOrUserNameContainingIgnoreCaseOrEmail(String searchParameter,String searchParameter1,String searchParameter2, Pageable pageable);
}
