package com.example.lab_2_testcontainers.repositories;

import com.example.lab_2_testcontainers.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}