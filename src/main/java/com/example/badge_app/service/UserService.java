package com.example.badge_app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.badge_app.entity.User;
import com.example.badge_app.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }
}

