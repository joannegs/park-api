package com.spring.demoparkapi.repository;

import com.spring.demoparkapi.entity.Client;
import com.spring.demoparkapi.entity.User;
import com.spring.demoparkapi.repository.projection.ClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c")
    Page<ClientProjection> findAllPageable(Pageable pageable);
}
