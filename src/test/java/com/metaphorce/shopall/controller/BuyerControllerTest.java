package com.metaphorce.shopall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metaphorce.shopall.dto.*;
import com.metaphorce.shopall.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BuyerController.class)
public class BuyerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CartService cartService;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private ProductReviewService productReviewService;

    @MockBean
    private UserService userService;

    @Test
    public void createSellerProfileTest() throws Exception {
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO(); // Configura los datos necesarios
        UserDTO userDTO = new UserDTO(); // Configura el objeto esperado
        when(userService.createUser(any(UserRegistrationDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/buyers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registrationDTO))) // Reemplazar con datos de prueba JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(userDTO.getUserName()));

        verify(userService).createUser(any(UserRegistrationDTO.class));
    }

    @Test
    public void browseProductsTest() throws Exception {
        List<ProductDTO> products = Collections.singletonList(new ProductDTO());
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/buyers/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists()); // Verifica que haya al menos un producto en la respuesta

        verify(productService).getAllProducts();
    }

    @Test
    public void searchProductTest() throws Exception {
        List<ProductDTO> foundProducts = Collections.singletonList(new ProductDTO());
        when(productService.searchProducts(anyString())).thenReturn(foundProducts);

        mockMvc.perform(get("/api/buyers/products/search")
                        .param("query", "example"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists()); // Verifica que haya al menos un producto en la respuesta

        verify(productService).searchProducts(anyString());
    }

    @Test
    public void searchProductsByCategoryTest() throws Exception {
        List<ProductDTO> foundProducts = Collections.singletonList(new ProductDTO());
        when(productService.searchProductsByCategory(anyString())).thenReturn(foundProducts);

        mockMvc.perform(get("/api/buyers/category/search")
                        .param("query", "example"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists()); // Verifica que haya al menos un producto en la respuesta

        verify(productService).searchProductsByCategory(anyString());
    }

    @Test
    public void addToCartTest() throws Exception {
        CartDTO cartDTO = new CartDTO(); // Configura los datos necesarios
        when(cartService.addCartItem(any(CartDTO.class))).thenReturn(cartDTO);

        mockMvc.perform(post("/api/buyers/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ /* Datos del carrito en JSON */ }")) // Reemplazar con datos de prueba JSON
                .andExpect(status().isOk());

        verify(cartService).addCartItem(any(CartDTO.class));
    }

    @Test
    public void getCartByUserIdTest() throws Exception {
        CartDTO cartDTO = new CartDTO(); // Configura el objeto esperado
        when(cartService.getCartByUserId(anyLong())).thenReturn(cartDTO);

        mockMvc.perform(get("/api/buyers/cart/" + 1)) // Reemplazar '1' con un userId de prueba
                .andExpect(status().isOk());

        verify(cartService).getCartByUserId(anyLong());
    }

    @Test
    public void removeCartItemTest() throws Exception {
        doNothing().when(cartService).removeCartItem(anyLong());

        mockMvc.perform(delete("/api/buyers/cart/" + 1)) // Reemplazar '1' con un cartItemId de prueba
                .andExpect(status().isNoContent());

        verify(cartService).removeCartItem(anyLong());
    }

    @Test
    public void completeTransactionTest() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO(); // Configura los datos necesarios
        when(transactionService.completeTransaction(any(TransactionDTO.class))).thenReturn(transactionDTO);

        mockMvc.perform(post("/api/buyers/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ /* Datos de la transacción en JSON */ }")) // Reemplazar con datos de prueba JSON
                .andExpect(status().isOk());

        verify(transactionService).completeTransaction(any(TransactionDTO.class));
    }

    @Test
    public void leaveProductReviewTest() throws Exception {
        ProductReviewDTO reviewDTO = new ProductReviewDTO(); // Configura los datos necesarios
        when(productReviewService.saveReview(any(ProductReviewDTO.class))).thenReturn(reviewDTO);

        mockMvc.perform(post("/api/buyers/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ /* Datos de la reseña en JSON */ }")) // Reemplazar con datos de prueba JSON
                .andExpect(status().isOk());

        verify(productReviewService).saveReview(any(ProductReviewDTO.class));
    }
}
