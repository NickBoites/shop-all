package com.metaphorce.shopall.controller;

import com.metaphorce.shopall.dto.*;
import com.metaphorce.shopall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyers")
public class BuyerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private UserService userService;


    // Crear un nuevo perfil de vendedor
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createSellerProfile(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserDTO newProfile = userService.createUser(userRegistrationDTO);
        return ResponseEntity.ok(newProfile);
    }

    // Navegar por categorías de productos
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> browseProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Buscar producto específico
    @GetMapping("/products/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam(name = "query") String query) {
        List<ProductDTO> foundProducts = productService.searchProducts(query);
        return ResponseEntity.ok(foundProducts);
    }

    // Buscar producto específico
    @GetMapping("/category/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByCategory(@RequestParam(name = "query") String query) {
        List<ProductDTO> foundProducts = productService.searchProductsByCategory(query);
        return ResponseEntity.ok(foundProducts);
    }

    // Agregar producto al carrito
    @PostMapping("/cart")
    public ResponseEntity<CartDTO> addToCart(@RequestBody CartDTO cartDto) {
        CartDTO updatedCart = cartService.addCartItem(cartDto);
        return ResponseEntity.ok(updatedCart);
    }

    // Ver carrito
    @GetMapping("/cart/{userId}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable Long userId) {
        CartDTO updatedCart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(updatedCart);
    }

    // Completar transacción
    @PostMapping("/transaction")
    public ResponseEntity<TransactionDTO> completeTransaction(@RequestBody TransactionDTO transactionDto) {
        TransactionDTO completedTransaction = transactionService.completeTransaction(transactionDto);
        return ResponseEntity.ok(completedTransaction);
    }

    // Dejar una reseña de producto
    @PostMapping("/review")
    public ResponseEntity<ProductReviewDTO> leaveProductReview(@RequestBody ProductReviewDTO reviewDto) {
        ProductReviewDTO savedReview = productReviewService.saveReview(reviewDto);
        return ResponseEntity.ok(savedReview);
    }
}
