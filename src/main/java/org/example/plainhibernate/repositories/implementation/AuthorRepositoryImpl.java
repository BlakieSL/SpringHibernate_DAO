package org.example.plainhibernate.repositories.implementation;


import org.example.plainhibernate.entities.Author;
import org.example.plainhibernate.repositories.declaration.AuthorRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Repository("authorRepository")
public class AuthorRepositoryImpl implements AuthorRepository {
    private final SessionFactory sessionFactory;

    public AuthorRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findByIdWithAssociations(long id) {
        return sessionFactory.getCurrentSession().createQuery(
                "SELECT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :id",
                Author.class
        ).setParameter("id", id).uniqueResultOptional();
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Author> findAll() {
        return new HashSet<>(sessionFactory.getCurrentSession().createQuery(
                "SELECT a FROM Author a LEFT JOIN FETCH a.books",
                Author.class
        ).list());
    }

    @Override
    public Author create(Author author) {
        sessionFactory.getCurrentSession().persist(author);
        return author;
    }

    @Override
    public Author update(long id, Author author) {
        Session session = sessionFactory.getCurrentSession();
        Author existingAuthor = session.createQuery(
                "SELECT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :id",
                Author.class
        ).setParameter("id", id).uniqueResult();

        if (existingAuthor == null) {
            throw new RuntimeException("Failed to update author");
        }

        existingAuthor.setFirstName(author.getFirstName());
        existingAuthor.setLastName(author.getLastName());

        return session.merge(existingAuthor);
    }

    @Override
    public boolean delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Author author = session.get(Author.class, id);

        if (author == null) {
            return false;
        }

        session.remove(author);
        return true;
    }
}
