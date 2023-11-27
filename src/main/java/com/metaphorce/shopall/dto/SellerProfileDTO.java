package com.metaphorce.shopall.dto;

import lombok.Data;

@Data
public class SellerProfileDTO {
    private Long sellerId;
    private String storeName;
    private String description;
    private String contactInfo;
    private UserDTO user;

    // Getters y Setters
}
