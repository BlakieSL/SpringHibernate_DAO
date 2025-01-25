package org.example.plainhibernate.repositories.declaration;

import org.example.plainhibernate.entities.Book;

import java.util.Optional;
import java.util.Set;

public interface BookRepository {
    Optional<Book> findByIdWithAssociations(Long id);
    Set<Book> findAll();
    Book create(Book book);
    Book update(long id, Book book);
    boolean delete(long id);
}
