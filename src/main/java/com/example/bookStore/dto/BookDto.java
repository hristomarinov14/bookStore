package com.example.bookStore.dto;

import com.example.bookStore.model.Author;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class BookDto {
    private Long id;
    private String title;
    private List<Long> author;
    private BigDecimal price;
    private Integer stockQuantity;
    private String isbn;
    private Long categoryId;

    public BookDto() {
    }

    public BookDto(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getAuthor() {
        return author;
    }

    public void setAuthor(List<Long> author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
