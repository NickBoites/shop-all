package com.metaphorce.shopall.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class ProductReviewDTO {
    private Long reviewId;

    @NotNull(message = "El producto es obligatorio")
    @Valid
    private ProductDTO product;

    @NotNull(message = "El usuario es obligatorio")
    @Valid
    private UserDTO user;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación debe ser al menos 1")
    @Max(value = 5, message = "La calificación debe ser como máximo 5")
    private Integer rating;

    @NotBlank(message = "El comentario es obligatorio")
    private String comment;

    private Date reviewDate;
}
