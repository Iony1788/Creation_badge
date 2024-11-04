package com.example.badge_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.badge_app.entity.User;



public interface UserRepository extends JpaRepository<User, Integer> {
	
	
	
	
}