package com.metaphorce.shopall.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionDetailId;

    @ManyToOne
    @JoinColumn(name = "transactionId")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private Double priceAtPurchase;

    private Integer quantity;

    // Getters y Setters
}
