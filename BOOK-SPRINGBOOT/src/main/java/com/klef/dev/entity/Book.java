package com.klef.dev.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_table")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate IDs
    @Column(name = "book_id")
    private int id;

    @Column(name = "book_title", nullable = false, length = 100)
    private String title;

    @Column(name = "book_author", nullable = false, length = 50)
    private String author;

    @Column(name = "book_genre", length = 30)
    private String genre;

    @Column(name = "book_publisher", length = 50)
    private String publisher;

    @Column(name = "book_year", length = 4)
    private String year;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
}
