package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.CartDTO;
import com.metaphorce.shopall.dto.CartItemDTO;
import com.metaphorce.shopall.model.Cart;
import com.metaphorce.shopall.model.CartItem;
import com.metaphorce.shopall.repository.CartItemRepository;
import com.metaphorce.shopall.repository.CartRepository;
import com.metaphorce.shopall.repository.ProductRepository;
import com.metaphorce.shopall.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public CartDTO addCartItem(CartDTO cartDto) {

        // Convertir el DTO a entidad
        Cart newCart = modelMapper.map(cartDto, Cart.class);

        // Buscar el carrito del usuario o crear uno nuevo si no existe
        Long userId = cartDto.getUser().getUserId();
        Cart cartDB = cartRepository.findByUserUserId(userId)
                .orElseGet(() -> {
                    userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
                    return cartRepository.save(newCart);

                });

        // Crear el nuevo CartItem y guardarlo
        CartItem newCartItem = modelMapper.map(newCart.getCartItems().get(0), CartItem.class);
        newCartItem.setCart(cartDB);
        CartItem savedNewCartItem = cartItemRepository.save(newCartItem);

        // Actualizar el carrito con el nuevo CartItem
        cartDB.getCartItems().clear();
        cartDB.getCartItems().add(savedNewCartItem);
        Cart savedCart = cartRepository.save(cartDB);

        // Convertir el carrito actualizado a DTO y devolverlo
        return modelMapper.map(savedCart, CartDTO.class);
    }

    public CartDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Carrito no encontrado"));

        CartDTO cartDto = modelMapper.map(cart, CartDTO.class);

        // Calcular el monto total de los artículos del carrito
        double totalAmount = cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // Agregar el monto total al DTO del carrito
        cartDto.setTotalAmount(totalAmount);

        return cartDto;
    }

    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
