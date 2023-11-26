package com.metaphorce.shopall.repository;

import com.metaphorce.shopall.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Métodos de consulta personalizados si se requieren
}
