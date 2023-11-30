package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.ProductCategoryDTO;
import com.metaphorce.shopall.model.ProductCategory;
import com.metaphorce.shopall.repository.CartRepository;
import com.metaphorce.shopall.repository.ProductCategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductCategoryServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ProductCategoryService productCategoryService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CartService cartService;

    @Test
    void getCategoryByName_ShouldReturnCategory() {
        String categoryName = "Electronics";
        ProductCategory category = new ProductCategory();
        category.setCategoryName(categoryName);
        when(productCategoryRepository.findByCategoryName(categoryName)).thenReturn(category);
        when(modelMapper.map(any(ProductCategory.class), eq(ProductCategoryDTO.class))).thenReturn(new ProductCategoryDTO());

        ProductCategoryDTO result = productCategoryService.getCategoryByName(categoryName);

        assertNotNull(result);
        verify(productCategoryRepository).findByCategoryName(categoryName);
    }

    @Test
    void createCategory_ShouldCreateCategory() {
        ProductCategoryDTO categoryDto = new ProductCategoryDTO();
        categoryDto.setCategoryName("Electronics");
        ProductCategory category = new ProductCategory();
        when(modelMapper.map(any(ProductCategoryDTO.class), eq(ProductCategory.class))).thenReturn(category);
        when(productCategoryRepository.findByCategoryName(anyString())).thenReturn(null);
        when(productCategoryRepository.save(any(ProductCategory.class))).thenReturn(category);
        when(modelMapper.map(any(ProductCategory.class), eq(ProductCategoryDTO.class))).thenReturn(categoryDto);

        ProductCategoryDTO result = productCategoryService.createCategory(categoryDto);

        assertNotNull(result);
        verify(productCategoryRepository).save(any(ProductCategory.class));
    }

    @Test
    void updateCategory_ShouldUpdateCategory() {
        Long categoryId = 1L;
        ProductCategoryDTO categoryDto = new ProductCategoryDTO();
        ProductCategory existingCategory = new ProductCategory();
        when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(productCategoryRepository.save(any(ProductCategory.class))).thenReturn(existingCategory);
        when(modelMapper.map(any(ProductCategory.class), eq(ProductCategoryDTO.class))).thenReturn(categoryDto);

        ProductCategoryDTO result = productCategoryService.updateCategory(categoryId, categoryDto);

        assertNotNull(result);
        verify(productCategoryRepository).save(any(ProductCategory.class));
    }

    @Test
    void getAllCategories_ShouldReturnAllCategories() {
        List<ProductCategory> categories = Arrays.asList(new ProductCategory(), new ProductCategory());
        when(productCategoryRepository.findAll()).thenReturn(categories);
        when(modelMapper.map(any(ProductCategory.class), eq(ProductCategoryDTO.class))).thenReturn(new ProductCategoryDTO());

        List<ProductCategoryDTO> result = productCategoryService.getAllCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productCategoryRepository).findAll();
    }

    @Test
    void deleteCategory_ShouldDeleteCategory() {
        Long categoryId = 1L;
        when(productCategoryRepository.existsById(categoryId)).thenReturn(true);
        doNothing().when(productCategoryRepository).deleteById(categoryId);

        productCategoryService.deleteCategory(categoryId);

        verify(productCategoryRepository).deleteById(categoryId);
    }

}
