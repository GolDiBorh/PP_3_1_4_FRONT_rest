package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    public  User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void saveUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional
    public void updateUser( User user) {
        userRepository.save(user);

    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
//    public User(String username, String password, String email, int age, Set<Role> roles) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.age = age;
//        this.roles = roles;
//    }
    public void setInitData() {
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        userRepository.save(new User("user","user", "user@mail.ru", 33, new HashSet<Role>() {{
            add(userRole);
        }}));
        userRepository.save(new User("admin", "admin", "admin@mail.ru", 33, new HashSet<Role>() {{
            add(userRole);
            add(adminRole);
        }}));
    }
}
