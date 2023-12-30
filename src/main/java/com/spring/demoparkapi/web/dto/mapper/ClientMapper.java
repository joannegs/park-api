package com.spring.demoparkapi.web.dto.mapper;


import com.spring.demoparkapi.entity.Client;
import com.spring.demoparkapi.entity.User;
import com.spring.demoparkapi.web.dto.ClientCreateDto;
import com.spring.demoparkapi.web.dto.ClientResponseDto;
import com.spring.demoparkapi.web.dto.UserResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {
    public static Client toClient(ClientCreateDto clientDto){
        return new ModelMapper().map(clientDto, Client.class);
    }

    public static ClientResponseDto toDto(Client client){
        return new ModelMapper().map(client, ClientResponseDto.class);
    }

    public static List<ClientResponseDto> toListDto(List<Client> clients) {
        return clients.stream().map(ClientMapper::toDto).collect(Collectors.toList());
    }
}
