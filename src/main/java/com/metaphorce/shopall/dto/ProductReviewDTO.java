package com.metaphorce.shopall.dto;

import lombok.Data;

import java.util.Date;
@Data
public class ProductReviewDTO {
    private Long reviewId;
    private ProductDTO product;
    private UserDTO user;
    private Integer rating;
    private String comment;
    private Date reviewDate;

    // Getters y Setters
}
