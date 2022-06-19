package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Column;

@Component
public class DBsave {
    private final UserService userService;

    public DBsave(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void setData() {
        userService.addDefaultUser();
    }
}
