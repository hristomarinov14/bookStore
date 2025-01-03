package com.example.bookStore.controller;

import com.example.bookStore.dto.BookDto;
import com.example.bookStore.model.Book;
import com.example.bookStore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        List<Book> products = bookService.getAllProducts();
        return bookService.convertToDtoList(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        Book product = bookService.getProductById(id);
        BookDto bookDto = bookService.convertToDto(product);
        return ResponseEntity.ok(bookDto);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        Book product = bookService.convertToEntity(bookDto);
        Book createdProduct = bookService.createProduct(product);
        BookDto createdBookDto = bookService.convertToDto(createdProduct);
        return ResponseEntity.ok(createdBookDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable long id, @RequestBody BookDto bookDto) {
        Book product = bookService.convertToEntity(bookDto);
        Book updatedProduct = bookService.updateProduct(id, product);
        BookDto updatedBookDto = bookService.convertToDto(updatedProduct);
        return ResponseEntity.ok(updatedBookDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        bookService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}