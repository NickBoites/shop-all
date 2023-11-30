package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.TransactionDTO;
import com.metaphorce.shopall.dto.TransactionDetailsDTO;
import com.metaphorce.shopall.model.Product;
import com.metaphorce.shopall.model.Transaction;
import com.metaphorce.shopall.repository.ProductRepository;
import com.metaphorce.shopall.repository.SellerProfileRepository;
import com.metaphorce.shopall.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionService transactionService;


    @Mock
    private ProductRepository productRepository;


    @Mock
    private ModelMapper modelMapper;

    @Test
    void completeTransaction_ShouldCompleteTransaction() {
        TransactionDTO transactionDto = new TransactionDTO();
        Transaction newTransaction = new Transaction();
        when(modelMapper.map(any(TransactionDTO.class), eq(Transaction.class))).thenReturn(newTransaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(newTransaction);
        doNothing().when(transactionService).updateInventory(any(TransactionDTO.class));

        TransactionDTO result = transactionService.completeTransaction(transactionDto);

        assertNotNull(result);
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    void updateInventory_WhenStockIsSufficient_ShouldUpdateInventory() {
        TransactionDTO transactionDto = new TransactionDTO();
        List<TransactionDetailsDTO> detailsList = Collections.singletonList(new TransactionDetailsDTO());
        transactionDto.setTransactionDetails(detailsList);
        Product product = new Product();
        product.setStock(10);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).save(any(Product.class));

        assertDoesNotThrow(() -> transactionService.updateInventory(transactionDto));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getTransactionsForSeller_ShouldReturnTransactions() {
        Long sellerId = 1L;
        List<Transaction> transactions = Arrays.asList(new Transaction(), new Transaction());
        when(transactionRepository.findBySellerSellerId(sellerId)).thenReturn(transactions);
        when(modelMapper.map(any(Transaction.class), eq(TransactionDTO.class))).thenReturn(new TransactionDTO());

        List<TransactionDTO> result = transactionService.getTransactionsForSeller(sellerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository).findBySellerSellerId(sellerId);
    }


}
