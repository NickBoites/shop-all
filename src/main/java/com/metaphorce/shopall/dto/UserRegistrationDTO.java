package com.metaphorce.shopall.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegistrationDTO extends UserDTO {
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}