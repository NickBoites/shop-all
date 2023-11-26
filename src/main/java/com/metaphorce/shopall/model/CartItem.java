package com.metaphorce.shopall.model;

import com.metaphorce.shopall.model.Cart;
import com.metaphorce.shopall.model.Product;

import javax.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private Integer quantity;

    // Getters y Setters
}