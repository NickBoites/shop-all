package com.metaphorce.shopall.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "sellerId")
    private SellerProfile seller;

    @NotBlank
    private String name;

    @Lob
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private ProductCategory category;

    // Getters y Setters
}
