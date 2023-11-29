package com.metaphorce.shopall.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartDTO {
    private Long cartId;

    @NotNull(message = "El usuario no puede ser nulo")
    @Valid
    private UserDTO user;

    @Valid
    private List<CartItemDTO> cartItems;

    private Double totalAmount;

}