package com.metaphorce.shopall.dto;

import com.metaphorce.shopall.model.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {
    private Long userId;

    @NotNull(message = "El nombre de usuario es obligatorio")
    private String userName;

    @Email(message = "El email debe ser válido")
    private String email;

    @NotNull(message = "El rol es obligatorio")
    private Role role;
}
