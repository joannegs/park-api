package com.spring.demoparkapi.service;


import com.spring.demoparkapi.entity.Client;
import com.spring.demoparkapi.exception.CpfUniqueViolationException;
import com.spring.demoparkapi.exception.EntityNotFoundException;
import com.spring.demoparkapi.repository.ClientRepository;
import com.spring.demoparkapi.repository.projection.ClientProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client save(Client client) {
        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException exception) {
            throw new CpfUniqueViolationException(String.format(
                    "CPF '%s' is already in the system. It can not be signed again.", client.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Client getById(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Client with id %s not found", id)));
    }

    @Transactional(readOnly = true)
    public Page<ClientProjection> getAll(Pageable pageable) {
        return clientRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Client getUserId(Long id) {
        return clientRepository.findByUserId(id);
    }

    @Transactional(readOnly = true)
    public Client findByCpf(String cpf) {
        return clientRepository.getByCpf(cpf).orElseThrow(
                () -> new EntityNotFoundException(String.format("Client with CPF %s not found", cpf)));
    }
}
