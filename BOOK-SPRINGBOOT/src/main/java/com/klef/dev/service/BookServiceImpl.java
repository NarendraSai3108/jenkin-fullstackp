package com.klef.dev.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klef.dev.entity.Book;
import com.klef.dev.repository.BookRepository;

@Service
public class BookServiceImpl implements Bookservice {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        book.setId(0); // Ensure ID is 0 so DB auto-generates
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(int id) {
        Optional<Book> opt = bookRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
}
