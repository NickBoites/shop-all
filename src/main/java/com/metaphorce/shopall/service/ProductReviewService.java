package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.ProductReviewDTO;
import com.metaphorce.shopall.model.ProductReview;
import com.metaphorce.shopall.repository.ProductRepository;
import com.metaphorce.shopall.repository.ProductReviewRepository;
import com.metaphorce.shopall.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductReviewService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductReviewDTO saveReview(ProductReviewDTO reviewDto) {
        // Verificar que el usuario existe
        Long userId = reviewDto.getUser().getUserId();
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        // Verificar que el producto existe
        Long productId = reviewDto.getProduct().getProductId();
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Producto no encontrado"));

        // Convertir DTO a entidad y guardar
        ProductReview productReview = modelMapper.map(reviewDto, ProductReview.class);
        ProductReview savedReview = productReviewRepository.save(productReview);

        return modelMapper.map(productReview, ProductReviewDTO.class)  ;
    }
}
