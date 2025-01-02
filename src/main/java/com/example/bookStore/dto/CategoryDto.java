package com.example.bookStore.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class CategoryDto {
    @NotBlank(message = "Category name is required")
    private String name;

    private Set<Long> bookIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(Set<Long> bookIds) {
        this.bookIds = bookIds;
    }
}

