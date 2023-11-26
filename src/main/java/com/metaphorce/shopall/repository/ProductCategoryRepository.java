package com.metaphorce.shopall.repository;

import com.metaphorce.shopall.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    // Métodos de consulta personalizados si se requieren
}
