package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Book {
	private final String title;
	private final String author;
	private final LocalDateTime published;

    public Book(String title, String author, LocalDateTime published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public LocalDateTime getPublished() {
        return published;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}