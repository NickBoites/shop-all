package com.metaphorce.shopall.repository;

import com.metaphorce.shopall.model.SellerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerProfileRepository extends JpaRepository<SellerProfile, Long> {
    // Métodos de consulta personalizados si se requieren
}
