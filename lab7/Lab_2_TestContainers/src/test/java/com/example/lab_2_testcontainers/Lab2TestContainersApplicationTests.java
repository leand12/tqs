package com.example.lab_2_testcontainers;

import com.example.lab_2_testcontainers.models.Book;
import com.example.lab_2_testcontainers.repositories.BookRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@SpringBootTest
class Lab2TestContainersApplicationTests {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withUsername("duke")
            .withPassword("password")
            .withDatabaseName("test");

    @Autowired
    private BookRepository bookRepository;

    // requires Spring Boot >= 2.2.6
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    @Order(1)
    public void testSaveBooks() {
        List<Book> bookList = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            Book b = new Book().setId(i).setName(String.format("Book #%s", i));
            bookList.add(b);
        }
        bookRepository.saveAll(bookList);
        bookRepository.flush();
        assertThat(bookRepository.count()).isEqualTo(10);
    }
    
    @Test
    @Order(2)
    public void testDeleteBook() {
        bookRepository.deleteById(1L);

        List<Book> books = bookRepository.findAll();
        assertThat(books)
                .hasSize(9)
                .extracting(Book::getId)
                .doesNotContain(1L);
    }

}

