package com.metaphorce.shopall.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SellerProfileRegistrationDTO {
    private Long sellerId;

    @NotBlank(message = "El nombre de la tienda es obligatorio")
    private String storeName;

    private String description;

    @NotBlank(message = "La información de contacto es obligatoria")
    private String contactInfo;

    @NotNull(message = "El usuario es obligatorio")
    @Valid
    private UserRegistrationDTO user;
}
