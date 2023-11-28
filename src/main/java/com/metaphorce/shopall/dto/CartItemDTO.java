package com.metaphorce.shopall.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long cartItemId;
    private ProductDTO product;
    private Integer quantity;

    // Getters y Setters
}