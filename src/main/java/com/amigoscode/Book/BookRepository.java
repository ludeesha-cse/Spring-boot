package com.amigoscode.Book;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {
    public List<Book> getAllBooks() {
        return List.of(
                new Book("Harry Potter", "J.K. Rowling"),
                new Book("Lord of the Rings", "J.R.R. Tolkien")
        );
    }
}
