package com.metaphorce.shopall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metaphorce.shopall.dto.*;
import com.metaphorce.shopall.service.ProductService;
import com.metaphorce.shopall.service.SellerProfileService;
import com.metaphorce.shopall.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SellerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SellerProfileService sellerProfileService;

    @Mock
    private ProductService productService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private SellerController sellerController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sellerController).build();
    }

    @Test
    public void createSellerProfileTest() throws Exception {
        SellerProfileRegistrationDTO registrationDTO = new SellerProfileRegistrationDTO(); // Asumiendo constructor adecuado o setters
        SellerProfileDTO profileDTO = new SellerProfileDTO(); // Asumiendo constructor adecuado o setters

        Mockito.when(sellerProfileService.createSellerProfile(any(SellerProfileRegistrationDTO.class))).thenReturn(profileDTO);

        mockMvc.perform(post("/api/sellers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registrationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.userName").value(profileDTO.getUser().getUserName()));

    }

    @Test
    public void getSellerProfileTest() throws Exception {
        Long sellerId = 1L;
        SellerProfileDTO profileDTO = new SellerProfileDTO(); // Asumiendo constructor adecuado o setters

        Mockito.when(sellerProfileService.getSellerById(sellerId)).thenReturn(profileDTO);

        mockMvc.perform(get("/api/sellers/" + sellerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.userName").value(profileDTO.getUser().getUserName()));
    }

    @Test
    public void updateSellerProfileTest() throws Exception {
        Long sellerId = 1L;
        SellerProfileDTO requestDTO = new SellerProfileDTO();
        SellerProfileDTO responseDTO = new SellerProfileDTO();

        Mockito.when(sellerProfileService.updateSellerProfile(eq(sellerId), any(SellerProfileDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/sellers/" + sellerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.userName").value(responseDTO.getUser().getUserName()));
    }

    @Test
    public void addProductTest() throws Exception {
        ProductDTO productDTO = new ProductDTO(); // Asumiendo constructor adecuado o setters
        Mockito.when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/sellers/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDTO.getName())); // Verifica algún campo específico en la respuesta
    }

    @Test
    public void getSellerProductsTest() throws Exception {
        Long sellerId = 1L;
        List<ProductDTO> productDTOList = Arrays.asList(new ProductDTO()); // Asumiendo constructor adecuado o setters

        Mockito.when(productService.getProductsForSeller(sellerId)).thenReturn(productDTOList);

        mockMvc.perform(get("/api/sellers/products/" + sellerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(productDTOList.get(0).getName())); // Verifica algún campo específico en la respuesta
    }

    @Test
    public void updateProductTest() throws Exception {
        Long productId = 1L;
        ProductDTO requestDTO = new ProductDTO(); // Asumiendo constructor adecuado o setters
        ProductDTO responseDTO = new ProductDTO(); // Asumiendo constructor adecuado o setters

        Mockito.when(productService.updateProduct(eq(productId), any(ProductDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/sellers/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(responseDTO.getName())); // Verifica algún campo específico en la respuesta
    }

    @Test
    public void deleteProductTest() throws Exception {
        Long productId = 1L;
        Mockito.doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/sellers/products/" + productId))
                .andExpect(status().isOk());
    }

    @Test
    public void getSellerTransactionsTest() throws Exception {
        Long sellerId = 1L;
        List<TransactionDTO> transactionDTOList = Arrays.asList(new TransactionDTO()); // Asumiendo constructor adecuado o setters

        Mockito.when(transactionService.getTransactionsForSeller(sellerId)).thenReturn(transactionDTOList);

        mockMvc.perform(get("/api/sellers/" + sellerId + "/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shippingAddress").value(transactionDTOList.get(0).getShippingAddress())); // Verifica algún campo específico en la respuesta
    }
}
