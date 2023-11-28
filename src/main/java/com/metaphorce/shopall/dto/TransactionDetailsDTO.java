package com.metaphorce.shopall.dto;

import lombok.Data;

@Data
public class TransactionDetailsDTO {
    private Long transactionDetailId;
    private Long productId;
    private Double priceAtPurchase;
    private Integer quantity;

    // Getters y Setters
}