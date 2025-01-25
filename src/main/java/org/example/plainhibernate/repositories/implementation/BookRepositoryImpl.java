package org.example.plainhibernate.repositories.implementation;


import org.example.plainhibernate.entities.Book;
import org.example.plainhibernate.repositories.declaration.BookRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Transactional
@Repository("bookRepository")
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findByIdWithAssociations(Long id) {
        return sessionFactory.getCurrentSession().createQuery(
                "SELECT b FROM Book b " +
                        "LEFT JOIN FETCH b.author " +
                        "LEFT JOIN FETCH b.libraries " +
                        "WHERE b.id = :id",
                Book.class
        ).setParameter("id", id).uniqueResultOptional();
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Book> findAll() {
        return new HashSet<>(sessionFactory.getCurrentSession().createQuery(
                "SELECT b FROM Book b",
                Book.class
        ).list());
    }

    @Override
    public Book create(Book book) {
        sessionFactory.getCurrentSession().persist(book);
        return book;
    }

    @Override
    public Book update(long id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        Book existingBook = session.get(Book.class, id);

        if (existingBook == null) {
            throw new RuntimeException("Failed to update book");
        }

        existingBook.setTitle(book.getTitle());
        existingBook.setReleaseDate(book.getReleaseDate());
        existingBook.setAuthor(book.getAuthor());

        return session.merge(existingBook);
    }

    @Override
    public boolean delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);

        if (book == null) {
            return false;
        }

        session.remove(book);
        return true;
    }
}

