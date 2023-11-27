package com.metaphorce.shopall.dto;

import com.metaphorce.shopall.model.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String userName;
    private String email;
    private Role role;

    // Getters y Setters
}
