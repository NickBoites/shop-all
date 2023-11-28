package com.metaphorce.shopall.repository;

import com.metaphorce.shopall.model.Cart;
import com.metaphorce.shopall.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    Optional<Cart> findByProductProductId(Long userId);
}
