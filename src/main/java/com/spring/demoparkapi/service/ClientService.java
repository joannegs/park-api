package com.spring.demoparkapi.service;


import com.spring.demoparkapi.entity.Client;
import com.spring.demoparkapi.exception.CpfUniqueViolationException;
import com.spring.demoparkapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
