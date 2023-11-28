package com.metaphorce.shopall.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long cartId;
    private UserDTO user;
    private List<CartItemDTO> cartItems;
    private Double totalAmount;

}