package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.ProductCategoryDTO;
import com.metaphorce.shopall.exception.CategoryNotFoundException;
import com.metaphorce.shopall.model.ProductCategory;
import com.metaphorce.shopall.repository.ProductCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener una categoría por nombre
    public ProductCategoryDTO getCategoryByName(String categoryName) {
        ProductCategory category = productCategoryRepository.findByCategoryName(categoryName);
        if (category != null) {
            return modelMapper.map(category, ProductCategoryDTO.class);
        } else {
            throw new CategoryNotFoundException("Categoría no encontrada: " + categoryName);
        }
    }
    // Método para crear una nueva categoría
    public ProductCategoryDTO createCategory(ProductCategoryDTO categoryDto) {
        ProductCategory category = modelMapper.map(categoryDto, ProductCategory.class);

        try {
            ProductCategory existingCategory = productCategoryRepository.findByCategoryName(category.getCategoryName());
            if (existingCategory == null) {
                ProductCategory savedCategory = productCategoryRepository.save(category);
                return modelMapper.map(savedCategory, ProductCategoryDTO.class);
            }else {
                throw new RuntimeException("Categoría ya existe: " + category.getCategoryName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage().toLowerCase(Locale.ROOT));
        }

    }

    // Método para actualizar una categoría existente
    public ProductCategoryDTO updateCategory(Long id, ProductCategoryDTO categoryDto) {
        ProductCategory existingCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada: " + id));

        modelMapper.map(categoryDto, existingCategory);
        ProductCategory updatedCategory = productCategoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, ProductCategoryDTO.class);
    }

    // Método para obtener todas las categorías
    public List<ProductCategoryDTO> getAllCategories() {
        List<ProductCategory> categories = productCategoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, ProductCategoryDTO.class))
                .collect(Collectors.toList());
    }

    // Método para eliminar una categoría
    public void deleteCategory(Long id) {
        if (!productCategoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Categoría no encontrada: " + id);
        }
        productCategoryRepository.deleteById(id);
    }
}
