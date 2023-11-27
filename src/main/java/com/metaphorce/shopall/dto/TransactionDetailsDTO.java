package com.metaphorce.shopall.dto;

import lombok.Data;

@Data
public class TransactionDetailsDTO {
    private Long transactionDetailId;
    private TransactionDTO transaction;
    private ProductDTO product;
    private Double priceAtPurchase;
    private Integer quantity;

    // Getters y Setters
}