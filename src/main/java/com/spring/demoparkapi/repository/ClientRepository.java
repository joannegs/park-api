package com.spring.demoparkapi.repository;

import com.spring.demoparkapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> { }
