package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.TransactionDTO;
import com.metaphorce.shopall.dto.TransactionDetailsDTO;
import com.metaphorce.shopall.exception.InsufficientStockException;
import com.metaphorce.shopall.exception.ProductNotFoundException;
import com.metaphorce.shopall.exception.TransactionProcessingException;
import com.metaphorce.shopall.model.Product;
import com.metaphorce.shopall.model.Transaction;
import com.metaphorce.shopall.model.TransactionDetails;
import com.metaphorce.shopall.repository.ProductRepository;
import com.metaphorce.shopall.repository.TransactionDetailsRepository;
import com.metaphorce.shopall.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    public TransactionDTO completeTransaction(TransactionDTO transactionDto) {

        // Actualizar inventario
        try {
            updateInventory(transactionDto);
        } catch (InsufficientStockException | ProductNotFoundException e) {
            throw new TransactionProcessingException("Error al procesar la transacción: " + e.getMessage());
        }

        // Convertir DTO a entidad
        Transaction newTransaction = modelMapper.map(transactionDto, Transaction.class);
        Date date = new Date();
        newTransaction.setTransactionDate(date);
        Transaction transaction = transactionRepository.save(newTransaction);

        // Guardar detalles de la transacción
        List <TransactionDetails> transactionDetailsList = new ArrayList<>();
        for (TransactionDetailsDTO details : transactionDto.getTransactionDetails()) {
            Long productId = details.getProductId();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

            TransactionDetails transactionDetails = new TransactionDetails();
            transactionDetails.setTransaction(transaction);
            transactionDetails.setProduct(product);
            transactionDetails.setQuantity(details.getQuantity());
            transactionDetails.setPriceAtPurchase(details.getPriceAtPurchase());

            TransactionDetails transactionDetailsSaved = transactionDetailsRepository.save(transactionDetails);

            transactionDetailsList.add(transactionDetailsSaved);
        }

        // Guardar transacción
        transaction.getTransactionDetails().clear();
        transaction.setTransactionDetails(transactionDetailsList);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Convertir entidad guardada a DTO y retornar
        TransactionDTO savedTransactionDto = modelMapper.map(savedTransaction, TransactionDTO.class);
        return savedTransactionDto;
    }

    public void updateInventory(TransactionDTO transactionDto) {
        for (TransactionDetailsDTO details : transactionDto.getTransactionDetails()) {
            Long productId = details.getProductId();
            Integer quantityPurchased = details.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

            if (product.getStock() < quantityPurchased) {
                throw new InsufficientStockException("Stock insuficiente para el producto: " + productId);
            }

            product.setStock(product.getStock() - quantityPurchased);
            productRepository.save(product);
        }
    }

    public List<TransactionDTO> getTransactionsForSeller(Long sellerId) {
        List<Transaction> transactions = transactionRepository.findBySellerSellerId(sellerId);
        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
    }
}
