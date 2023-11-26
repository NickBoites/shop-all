package com.metaphorce.shopall.repository;

import com.metaphorce.shopall.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Métodos de consulta personalizados si se requieren
}
