package com.spring.demoparkapi.web.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ClientCreateDto {

    @NonNull
    @Size(min=5, max=100)
    private String name;

    @NonNull
    @Size(min=11, max=11)
    @CPF
    private String cpf;
}
