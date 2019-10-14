package com.edu.mum.repository;

import com.edu.mum.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository  extends JpaRepository<Role,Integer> {
    public List<Role> findAll();
}
