package com.klef.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.dev.entity.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByGenre(String genre);
    List<Book> findByYear(String year);
    Optional<Book> findOneByTitle(String title);
    List<Book> findByTitleIgnoreCase(String title);
}
