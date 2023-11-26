package com.metaphorce.shopall.repository;

import com.metaphorce.shopall.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Métodos de consulta personalizados si se requieren
}
