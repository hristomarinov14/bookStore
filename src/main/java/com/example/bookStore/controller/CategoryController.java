package com.example.bookStore.controller;

import com.example.bookStore.dto.CategoryDto;
import com.example.bookStore.model.Category;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookRepository bookRepository;

    public CategoryController(CategoryService categoryService, BookRepository bookRepository) {
        this.categoryService = categoryService;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setBooks(categoryDto.getBookIds().stream().map(bookRepository::findBookById).collect(Collectors.toSet()));
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category categoryDetails = new Category(id);
        categoryDetails.setName(categoryDto.getName());

        categoryDetails.setBooks(categoryDto.getBookIds().stream().map(bookRepository::findBookById).collect(Collectors.toSet()));

        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
