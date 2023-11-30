package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.ProductCategoryDTO;
import com.metaphorce.shopall.dto.ProductDTO;
import com.metaphorce.shopall.dto.SellerProfileDTO;
import com.metaphorce.shopall.model.Product;
import com.metaphorce.shopall.model.ProductCategory;
import com.metaphorce.shopall.model.SellerProfile;

import com.metaphorce.shopall.repository.ProductRepository;
import com.metaphorce.shopall.repository.SellerProfileRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    @Mock
    private SellerProfileRepository sellerProfileRepository;

    @Mock
    private ProductCategoryService productCategoryService;

    @Mock
    private SellerProfileService sellerService;


    @Mock
    private ModelMapper modelMapper;



    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(products);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(new ProductDTO());

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(new ProductDTO());

        ProductDTO result = productService.getProductById(productId);

        assertNotNull(result);
        verify(productRepository).findById(productId);
    }

    @Test
    void createProduct_WhenSellerExists_ShouldCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();
        when(modelMapper.map(any(ProductDTO.class), eq(Product.class))).thenReturn(product);
        when(sellerProfileRepository.existsById(anyLong())).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertNotNull(result);
        verify(productRepository).save(any(Product.class));
    }


    @Test
    void updateProduct_WhenProductExists_ShouldUpdateProduct() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        Product existingProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

        ProductDTO result = productService.updateProduct(productId, productDTO);

        assertNotNull(result);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct_WhenProductExists_ShouldDeleteProduct() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any(Product.class));

        productService.deleteProduct(productId);

        verify(productRepository).delete(any(Product.class));
    }

    @Test
    void searchProducts_ShouldReturnMatchingProducts() {
        String query = "Test";
        List<Product> foundProducts = Arrays.asList(new Product(), new Product());
        when(productRepository.findByNameContainingIgnoreCase(query)).thenReturn(foundProducts);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(new ProductDTO());

        List<ProductDTO> result = productService.searchProducts(query);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository).findByNameContainingIgnoreCase(query);
    }

    @Test
    void searchProductsByCategory_ShouldReturnMatchingProducts() {
        String categoryName = "Electronics";
        ProductCategory category = new ProductCategory();
        List<Product> foundProducts = Arrays.asList(new Product(), new Product());
        when(productCategoryService.getCategoryByName(categoryName)).thenReturn(new ProductCategoryDTO());
        when(modelMapper.map(any(ProductCategoryDTO.class), eq(ProductCategory.class))).thenReturn(category);
        when(productRepository.findByCategory(category)).thenReturn(foundProducts);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(new ProductDTO());

        List<ProductDTO> result = productService.searchProductsByCategory(categoryName);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository).findByCategory(category);
    }

    @Test
    void getProductsForSeller_ShouldReturnProductsForSeller() {
        Long sellerId = 1L;
        SellerProfile seller = new SellerProfile();
        List<Product> foundProducts = Arrays.asList(new Product(), new Product());
        when(sellerService.getSellerById(sellerId)).thenReturn(new SellerProfileDTO());
        when(modelMapper.map(any(SellerProfileDTO.class), eq(SellerProfile.class))).thenReturn(seller);
        when(productRepository.findBySeller(seller)).thenReturn(foundProducts);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(new ProductDTO());

        List<ProductDTO> result = productService.getProductsForSeller(sellerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository).findBySeller(seller);
    }


}
