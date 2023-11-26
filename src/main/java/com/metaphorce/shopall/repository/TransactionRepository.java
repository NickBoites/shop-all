package com.metaphorce.shopall.repository;

import com.metaphorce.shopall.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Métodos de consulta personalizados si se requieren
}
