package com.metaphorce.shopall.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class SellerProfile {
    @Id
    private Long sellerId;

    @NotBlank
    private String storeName;

    @Lob
    private String description;

    private String contactInfo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "sellerId")
    private User user;

    // Getters y Setters
}
