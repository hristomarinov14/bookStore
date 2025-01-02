package com.example.bookStore.repository;

import com.example.bookStore.model.Author;
import com.example.bookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT id FROM Book WHERE id = :authorId")
    Author findAuthorById(@Param("authorId") Long authorId);
}