package com.metaphorce.shopall.repository;

import com.metaphorce.shopall.model.Product;
import com.metaphorce.shopall.model.ProductCategory;
import com.metaphorce.shopall.model.SellerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory(ProductCategory category);

    List<Product> findBySeller(SellerProfile seller);

}
