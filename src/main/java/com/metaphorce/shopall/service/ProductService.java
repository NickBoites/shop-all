package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.ProductCategoryDTO;
import com.metaphorce.shopall.dto.ProductDTO;
import com.metaphorce.shopall.exception.ProductNotFoundException;
import com.metaphorce.shopall.model.Product;
import com.metaphorce.shopall.model.ProductCategory;
import com.metaphorce.shopall.model.SellerProfile;
import com.metaphorce.shopall.repository.ProductRepository;
import com.metaphorce.shopall.repository.SellerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerProfileService sellerService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener todos los productos
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    // Obtener un producto por ID
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con ID " + id + " no encontrado"));
        return modelMapper.map(product, ProductDTO.class);
    }

    // Crear un nuevo producto
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    // Actualizar un producto
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con ID " + id + " no encontrado para actualizar"));

        modelMapper.map(productDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    // Eliminar un producto
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con ID " + id + " no encontrado para eliminar"));

        productRepository.delete(product);
    }

    public List<ProductDTO> searchProducts(String query) {
        List<Product> foundProducts = productRepository.findByNameContainingIgnoreCase(query);
        return foundProducts.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchProductsByCategory(String categoryName) {
        ProductCategoryDTO categoryDTO = productCategoryService.getCategoryByName(categoryName);
        ProductCategory category = modelMapper.map(categoryDTO, ProductCategory.class);

        List<Product> foundProducts = productRepository.findByCategory(category);
        return foundProducts.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsForSeller(Long sellerId) {
        SellerProfile seller = modelMapper.map(sellerService.getSellerById(sellerId), SellerProfile.class);
        List<Product> foundProducts = productRepository.findBySeller(seller);
        return foundProducts.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }
}
