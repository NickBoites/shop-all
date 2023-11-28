package com.metaphorce.shopall.repository;

import com.metaphorce.shopall.model.Cart;
import com.metaphorce.shopall.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    @Query("SELECT t FROM Transaction t JOIN t.transactionDetails td JOIN td.product p WHERE p.seller.sellerId = :sellerId")
//    List<Transaction> findTransactionsBySellerId(Long sellerId);
    Optional<Transaction> findByUserUserId(Long userId);

    List<Transaction> findBySellerSellerId(Long userId);


}
