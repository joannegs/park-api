package com.spring.demoparkapi.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientResponseDto {
    private Long id;
    private String name;
    private String cpf;
}
