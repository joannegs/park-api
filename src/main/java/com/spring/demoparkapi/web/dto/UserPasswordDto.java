package com.spring.demoparkapi.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDto {
    private String currentPassword;
    private String updatedPassword;
    private String updatedPasswordConfirm;
}
