package com.metaphorce.shopall.repository;

import com.metaphorce.shopall.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Métodos de consulta personalizados si se requieren
}
