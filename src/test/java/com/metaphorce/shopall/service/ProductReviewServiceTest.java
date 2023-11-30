package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.ProductCategoryDTO;
import com.metaphorce.shopall.dto.ProductDTO;
import com.metaphorce.shopall.dto.ProductReviewDTO;
import com.metaphorce.shopall.dto.UserDTO;
import com.metaphorce.shopall.model.*;
import com.metaphorce.shopall.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductReviewServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ProductCategoryService productCategoryService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductReviewRepository productReviewRepository;

    @Mock
    private ProductReviewService productReviewService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CartService cartService;


    @Test
    void saveReview_WhenUserAndProductExist_ShouldSaveReview() {
        ProductReviewDTO reviewDto = new ProductReviewDTO();
        reviewDto.setUser(new UserDTO(1L, "user", "user@example.com", Role.COMPRADOR));
        reviewDto.setProduct(new ProductDTO(1L, "Product", "Description", 10.0, 5, new ProductCategoryDTO()));

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product()));
        when(productReviewRepository.save(any(ProductReview.class))).thenReturn(new ProductReview());
        when(modelMapper.map(any(ProductReviewDTO.class), eq(ProductReview.class))).thenReturn(new ProductReview());
        when(modelMapper.map(any(ProductReview.class), eq(ProductReviewDTO.class))).thenReturn(reviewDto);

        ProductReviewDTO result = productReviewService.saveReview(reviewDto);

        assertNotNull(result);
        verify(productReviewRepository).save(any(ProductReview.class));
    }

    @Test
    void saveReview_WhenUserNotFound_ShouldThrowException() {
        ProductReviewDTO reviewDto = new ProductReviewDTO();
        reviewDto.setUser(new UserDTO(1L, "user", "user@example.com", Role.COMPRADOR));
        reviewDto.setProduct(new ProductDTO(1L, "Product", "Description", 10.0, 5, new ProductCategoryDTO()));

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            productReviewService.saveReview(reviewDto);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void saveReview_WhenProductNotFound_ShouldThrowException() {
        ProductReviewDTO reviewDto = new ProductReviewDTO();
        reviewDto.setUser(new UserDTO(1L, "user", "user@example.com", Role.COMPRADOR));
        reviewDto.setProduct(new ProductDTO(1L, "Product", "Description", 10.0, 5, new ProductCategoryDTO()));

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            productReviewService.saveReview(reviewDto);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }

}
