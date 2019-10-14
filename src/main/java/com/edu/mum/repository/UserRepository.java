package com.edu.mum.repository;

import com.edu.mum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findDistinctByUserNameAndPassword(String user_name, String password);
}
