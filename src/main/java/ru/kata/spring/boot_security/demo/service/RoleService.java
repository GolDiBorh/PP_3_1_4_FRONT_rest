package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@Service("roleService")
@Transactional
public class RoleService {
    private RoleRepository roleRepository;


    @Autowired
    public RoleService( RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleById(long id) {
        return roleRepository.getById(id);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }
}
