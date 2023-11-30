package com.metaphorce.shopall.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.metaphorce.shopall.dto.UserDTO;
import com.metaphorce.shopall.model.Cart;
import com.metaphorce.shopall.model.CartItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.metaphorce.shopall.repository.CartRepository;
import com.metaphorce.shopall.repository.CartItemRepository;
import com.metaphorce.shopall.dto.CartDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CartService cartService;

    @Test
    void addCartItem_ShouldAddCartItem() {
        // Preparación de datos de prueba
        CartDTO cartDto = new CartDTO();
        UserDTO userDto = new UserDTO();
        userDto.setUserId(1L);
        cartDto.setUser(userDto);

        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cart.setCartItems(Collections.singletonList(cartItem));
        when(modelMapper.map(any(CartDTO.class), eq(Cart.class))).thenReturn(cart);
        when(cartRepository.findByUserUserId(anyLong())).thenReturn(Optional.of(cart));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        when(modelMapper.map(any(Cart.class), eq(CartDTO.class))).thenReturn(cartDto);

        // Ejecutar el método
        CartDTO result = cartService.addCartItem(cartDto);

        // Verificaciones y aserciones
        assertNotNull(result);
        verify(cartRepository).save(any(Cart.class));
    }


    @Test
    void getCartByUserId_ShouldReturnCart() {
        // Preparación de datos de prueba
        Long userId = 1L;
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>());
        when(cartRepository.findByUserUserId(userId)).thenReturn(Optional.of(cart));
        when(modelMapper.map(any(Cart.class), eq(CartDTO.class))).thenReturn(new CartDTO());

        // Ejecutar el método
        CartDTO result = cartService.getCartByUserId(userId);

        // Verificaciones y aserciones
        assertNotNull(result);
        assertEquals(0.0, result.getTotalAmount());
    }


    @Test
    void removeCartItem_ShouldRemoveCartItem() {
        // Preparación de datos de prueba
        Long cartItemId = 1L;
        doNothing().when(cartItemRepository).deleteById(cartItemId);

        // Ejecutar el método
        cartService.removeCartItem(cartItemId);

        // Verificaciones
        verify(cartItemRepository).deleteById(cartItemId);
    }

}
