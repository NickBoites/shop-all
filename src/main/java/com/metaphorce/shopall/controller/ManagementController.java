package com.metaphorce.shopall.controller;

import com.metaphorce.shopall.dto.ProductCategoryDTO;
import com.metaphorce.shopall.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/management")
public class ManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    // Crear una nueva categoría de producto
    @PostMapping("/categories")
    public ResponseEntity<ProductCategoryDTO> createCategory(@RequestBody ProductCategoryDTO categoryDto) {
        ProductCategoryDTO newCategory = productCategoryService.createCategory(categoryDto);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    // Actualizar una categoría de producto existente
    @PutMapping("/categories/{id}")
    public ResponseEntity<ProductCategoryDTO> updateCategory(@PathVariable Long id, @RequestBody ProductCategoryDTO categoryDto) {
        ProductCategoryDTO updatedCategory = productCategoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    // Obtener todas las categorías
    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategoryDTO>> getAllCategories() {
        List<ProductCategoryDTO> categories = productCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Eliminar una categoría
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        productCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Aquí puedes agregar más métodos para administrar otros aspectos de la plataforma
}
