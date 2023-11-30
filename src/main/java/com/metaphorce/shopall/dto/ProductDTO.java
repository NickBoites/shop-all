package com.metaphorce.shopall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private Long productId;

    @NotNull(message = "El perfil del vendedor es obligatorio")
    @Valid
    private SellerProfileDTO seller;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;

    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Double price;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private Integer stock;

    @NotNull(message = "La categoría del producto es obligatoria")
    @Valid
    private ProductCategoryDTO category;

    public ProductDTO(long l, String product, String description, double v, int i, ProductCategoryDTO productCategoryDTO) {
    }
}
