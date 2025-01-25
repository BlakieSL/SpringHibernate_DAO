package org.example.plainhibernate.repositories.implementation;

import org.example.plainhibernate.entities.Library;
import org.example.plainhibernate.entities.LibraryInfo;
import org.example.plainhibernate.repositories.declaration.LibraryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Transactional
@Repository("libraryRepository")
public class LibraryRepositoryImpl implements LibraryRepository {
    private final SessionFactory sessionFactory;

    public LibraryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Library> findById(Long id) {
        return sessionFactory.getCurrentSession().createQuery(
                "SELECT l FROM Library l " +
                        "LEFT JOIN FETCH l.libraryInfo " +
                        "LEFT JOIN FETCH l.books " +
                        "WHERE l.id = :id",
                Library.class
        ).setParameter("id", id).uniqueResultOptional();
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Library> findAll() {
        return new HashSet<>(sessionFactory.getCurrentSession().createQuery(
                "SELECT l FROM Library l " +
                        "LEFT JOIN FETCH l.books",
                Library.class
        ).list());
    }

    @Override
    public long create(Library library) {
        sessionFactory.getCurrentSession().persist(library);
        return library.getId();
    }

    @Override
    public void update(long id, Library library) {
        Session session = sessionFactory.getCurrentSession();
        Library existingLibrary = session.get(Library.class, id);

        if (existingLibrary == null) {
            throw new RuntimeException("Failed to update library");
        }

        LibraryInfo existingLibraryInfo = existingLibrary.getLibraryInfo();

        if (existingLibraryInfo == null) {
            throw new RuntimeException("Failed to update library");
        }

        existingLibrary.setName(library.getName());

        existingLibraryInfo.setAddress(library.getLibraryInfo().getAddress());
        existingLibraryInfo.setPhone(library.getLibraryInfo().getPhone());
        existingLibraryInfo.setLibrary(existingLibrary);

        session.merge(existingLibrary);
    }

    @Override
    public boolean delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Library library = session.get(Library.class, id);

        if (library == null) {
            return false;
        }

        session.remove(library);
        return true;
    }
}