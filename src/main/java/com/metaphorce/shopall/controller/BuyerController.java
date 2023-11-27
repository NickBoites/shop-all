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

    // Navegar por categorías de productos
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> browseProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Buscar producto específico
    @GetMapping("/products/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam String query) {
        List<ProductDTO> foundProducts = productService.searchProducts(query);
        return ResponseEntity.ok(foundProducts);
    }

    // Agregar producto al carrito
    @PostMapping("/cart")
    public ResponseEntity<CartDTO> addToCart(@RequestBody CartItemDTO cartItemDto) {
        CartDTO updatedCart = cartService.addCartItem(cartItemDto);
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

    // Aquí puedes agregar más métodos según las necesidades de ShopAll
}
