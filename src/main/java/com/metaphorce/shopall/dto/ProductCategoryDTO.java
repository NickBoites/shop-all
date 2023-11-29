package com.metaphorce.shopall.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductCategoryDTO {
    private Long categoryId;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String categoryName;

    private String description;
}