package org.example.plainhibernate.repositories.declaration;

import org.example.plainhibernate.entities.Author;

import java.util.Optional;
import java.util.Set;

public interface AuthorRepository {
    Optional<Author> findByIdWithAssociations(long id);
    Set<Author> findAll();
    Author create(Author author);
    Author update(long id, Author author);
    boolean delete(long id);
}
