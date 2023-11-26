package com.metaphorce.shopall.dto;

import java.util.Date;
import java.util.List;

public class TransactionDTO {
    private Long transactionId;
    private UserDTO user;
    private Double totalAmount;
    private Date transactionDate;
    private String shippingAddress;
    private String paymentDetails;
    private List<TransactionDetailsDTO> transactionDetails;

    // Getters y Setters
}
