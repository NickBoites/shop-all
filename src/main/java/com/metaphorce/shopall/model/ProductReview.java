package com.metaphorce.shopall.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Integer rating;

    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewDate;

    // Getters y Setters
}
