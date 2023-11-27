package com.metaphorce.shopall.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long productId;
    private SellerProfileDTO seller;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private ProductCategoryDTO category;

    // Getters y Setters
}
