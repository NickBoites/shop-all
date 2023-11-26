package com.metaphorce.shopall.model;

import javax.persistence.*;

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
