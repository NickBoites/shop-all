package com.metaphorce.shopall.exception;

public class SellerProfileNotFoundException extends RuntimeException {
    public SellerProfileNotFoundException(String message) {
        super(message);
    }
}