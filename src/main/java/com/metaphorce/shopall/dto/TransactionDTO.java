package com.metaphorce.shopall.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class TransactionDTO {
    private Long transactionId;

    @NotNull(message = "El usuario es obligatorio")
    private UserDTO user;

    @NotNull(message = "El vendedor es obligatorio")
    private SellerProfileDTO seller;

    @NotNull(message = "El monto total es obligatorio")
    private Double totalAmount;

    private Date transactionDate;

    @NotBlank(message = "La dirección de envío es obligatoria")
    private String shippingAddress;

    @NotBlank(message = "Los detalles de pago son obligatorios")
    private String paymentDetails;

    @Valid
    private List<TransactionDetailsDTO> transactionDetails;
}
