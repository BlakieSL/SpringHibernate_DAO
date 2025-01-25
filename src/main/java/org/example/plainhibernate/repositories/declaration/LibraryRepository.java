package org.example.plainhibernate.repositories.declaration;

import org.example.plainhibernate.entities.Library;

import java.util.Optional;
import java.util.Set;

public interface LibraryRepository {
    Optional<Library> findById(Long id);
    Set<Library> findAll();
    long create(Library library);
    void update(long id, Library library);
    boolean delete(long id);
}
