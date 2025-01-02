package com.example.bookStore.service;

import com.example.bookStore.dto.BookDto;
import com.example.bookStore.model.Author;
import com.example.bookStore.model.Book;
import com.example.bookStore.model.Category;
import com.example.bookStore.exception.ResourceNotFoundException;
import com.example.bookStore.repository.AuthorRepository;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository productRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllProducts() {
        return productRepository.findAll();
    }

    public Book getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Book createProduct(Book product) {
        return productRepository.save(product);
    }

    public Book updateProduct(Long id, Book bookDetails) {
        Book book = getProductById(id);

        book.setTitle(bookDetails.getTitle());
        book.setIsbn(bookDetails.getIsbn());
        book.setPrice(bookDetails.getPrice());
        book.setStockQuantity(bookDetails.getStockQuantity());
        book.setAuthor(bookDetails.getAuthor());

        return productRepository.save(book);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public BookDto convertToDto(Book book) {
        BookDto bookDto = new BookDto(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor().stream().map(Author::getId).collect(Collectors.toList()));
        bookDto.setPrice(book.getPrice());
        bookDto.setStockQuantity(book.getStockQuantity());
        bookDto.setIsbn(book.getIsbn());
        return bookDto;
    }

    public Book convertToEntity(BookDto bookDto) {
        Book product = new Book();
        product.setTitle(bookDto.getTitle());
        product.setAuthor(bookDto.getAuthor().stream().map(authorRepository::findAuthorById).collect(Collectors.toList()));
        product.setPrice(bookDto.getPrice());
        product.setIsbn(bookDto.getIsbn());
        product.setStockQuantity(bookDto.getStockQuantity());

        if (bookDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(bookDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            product.setCategory(category);
        }

        return product;
    }

    public List<BookDto> convertToDtoList(List<Book> products) {
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}