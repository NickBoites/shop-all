package com.metaphorce.shopall.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

}
