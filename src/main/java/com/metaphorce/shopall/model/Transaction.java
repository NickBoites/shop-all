package com.metaphorce.shopall.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Double totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    private String shippingAddress;

    private String paymentDetails;

    @OneToMany(mappedBy = "transaction")
    private List<TransactionDetails> transactionDetails;

    // Getters y Setters
}