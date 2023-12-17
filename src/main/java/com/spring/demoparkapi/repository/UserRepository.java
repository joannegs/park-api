package com.spring.demoparkapi.repository;

import com.spring.demoparkapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}