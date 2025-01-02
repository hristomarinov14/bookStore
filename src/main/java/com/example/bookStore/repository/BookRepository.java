package com.example.bookStore.repository;

import com.example.bookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategory_Name(String categoryName);

    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.book.id = :bookId")
    Long calculateTotalSales(@Param("bookId") Long bookId);

    @Query("SELECT id FROM Book WHERE id = :bookId")
    Book findBookById(@Param("bookId") Long bookId);
}
