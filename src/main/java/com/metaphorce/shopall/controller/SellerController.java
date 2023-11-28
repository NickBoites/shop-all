package com.metaphorce.shopall.controller;

import com.metaphorce.shopall.dto.ProductDTO;
import com.metaphorce.shopall.dto.SellerProfileDTO;
import com.metaphorce.shopall.dto.SellerProfileRegistrationDTO;
import com.metaphorce.shopall.dto.TransactionDTO;
import com.metaphorce.shopall.service.ProductService;
import com.metaphorce.shopall.service.SellerProfileService;
import com.metaphorce.shopall.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerProfileService sellerProfileService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionService transactionService;  // Servicio para manejar pedidos

    // Crear un nuevo perfil de vendedor
    @PostMapping("/register")
    public ResponseEntity<SellerProfileDTO> createSellerProfile(@RequestBody SellerProfileRegistrationDTO sellerProfileDto) {
        SellerProfileDTO newProfile = sellerProfileService.createSellerProfile(sellerProfileDto);
        return ResponseEntity.ok(newProfile);
    }

    // Obtener perfil del vendedor
    @GetMapping("/{sellerId}")
    public ResponseEntity<SellerProfileDTO> getSellerProfile(@PathVariable Long sellerId) {
        SellerProfileDTO sellerProfile = sellerProfileService.getSellerById(sellerId);
        return ResponseEntity.ok(sellerProfile);
    }

    // Actualizar perfil del vendedor
    @PutMapping("/{sellerId}")
    public ResponseEntity<SellerProfileDTO> updateSellerProfile(@PathVariable Long sellerId, @RequestBody SellerProfileDTO sellerProfileDto) {
        SellerProfileDTO updatedProfile = sellerProfileService.updateSellerProfile(sellerId, sellerProfileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    // Añadir un nuevo producto
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDto) {
        ProductDTO newProduct = productService.createProduct(productDto);
        return ResponseEntity.ok(newProduct);
    }

    // Obtener productos por vendedor
    @GetMapping("/products/{sellerId}")
    public ResponseEntity<List<ProductDTO>> getSellerProducts(@PathVariable Long sellerId) {
        List<ProductDTO> products = productService.getProductsForSeller(sellerId);
        return ResponseEntity.ok(products);
    }

    // Actualizar un producto existente
    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDto) {
        ProductDTO updatedProduct = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    // Eliminar un producto existente
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{sellerId}/transactions")
    public ResponseEntity<List<TransactionDTO>> getSellerTransactions(@PathVariable Long sellerId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsForSeller(sellerId);
        return ResponseEntity.ok(transactions);
    }
}
