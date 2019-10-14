package com.edu.mum.service.impl;

import com.edu.mum.domain.Role;
import com.edu.mum.repository.RoleRepository;
import com.edu.mum.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findRoles() {
        return roleRepository.findAll();
    }
}
