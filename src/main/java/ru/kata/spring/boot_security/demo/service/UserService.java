package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

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




    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new org.springframework.security.core.userdetails.User(
                findByUsername(username).getUsername(),
                passwordEncoder.encode(findByUsername(username).getPassword()),
                findByUsername(username).getRoles());
    }


    @Transactional
    public void saveUser(User user) {
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
}
