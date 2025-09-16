package com.klef.dev.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.klef.dev.entity.Book;
import com.klef.dev.service.BookServiceImpl;

@RestController
@RequestMapping("/bookapi")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;
    
    @GetMapping("/")
    public String home() {
        return "Book Management API - Jenkins Full Stack Deployment Demo";
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        try {
            bookService.addBook(book);
            return new ResponseEntity<>("Book added successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding book: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        Book book = bookService.getBookById(id);
        if (book != null) return new ResponseEntity<>(book, HttpStatus.OK);
        return new ResponseEntity<>("Book with ID " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateBook(@RequestBody Book book) {
        Book existing = bookService.getBookById(book.getId());
        if (existing != null) {
            bookService.updateBook(book);
            return new ResponseEntity<>("Book updated successfully.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cannot update. Book with ID " + book.getId() + " not found.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        Book existing = bookService.getBookById(id);
        if (existing != null) {
            bookService.deleteBookById(id);
            return new ResponseEntity<>("Book with ID " + id + " deleted successfully.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cannot delete. Book with ID " + id + " not found.", HttpStatus.NOT_FOUND);
    }
}
