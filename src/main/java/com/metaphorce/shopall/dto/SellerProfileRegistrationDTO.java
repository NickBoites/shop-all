package com.metaphorce.shopall.dto;

import lombok.Data;

@Data
public class SellerProfileRegistrationDTO {
    private Long sellerId;
    private String storeName;
    private String description;
    private String contactInfo;
    private UserRegistrationDTO user;

    // Getters y Setters
}
