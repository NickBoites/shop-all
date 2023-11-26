package com.metaphorce.shopall.repository;

import com.metaphorce.shopall.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    // Métodos de consulta personalizados si se requieren
}
