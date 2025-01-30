# SpringHibernate_DAO Repository 🌿

A Hibernate-based project demonstrating robust, performant database interactions using **Spring** for configuration and **Hibernate** as the ORM provider.  
*Bridging the power of native Hibernate Session management with Spring’s convenience for enterprise-grade persistence!*

---

## 📖 Overview

This repository showcases how to integrate **Spring** and **Hibernate** in a modular fashion, emphasizing fine-grained session handling, transaction boundaries, and custom HQL queries. By leaning on native SessionFactory APIs (rather than Spring Data JPA), it provides an educational glimpse into lower-level persistence control while still leveraging the Spring ecosystem for easier configuration and testing.

---

## ✨ Key Features

### 🧬 **Manual Hibernate Session Handling**
- **SessionFactory** injection for full access to Hibernate APIs  
- Explicit HQL usage (with `JOIN FETCH`) to efficiently load associations  
- Clearly defined transaction boundaries via `@Transactional` on custom Repositories  

### ⚡ **Custom Cascade & Relationship Management**
- Bidirectional relationships with `@OneToOne`, `@ManyToOne`, `@ManyToMany`  
- Cascading rules (`REMOVE`, `PERSIST`) ensuring consistent entity lifecycle  
- Conditional fetch strategies for better performance (e.g., lazy collections)  

### 🧪 **Integration Testing with Testcontainers**
- **@SpringBootTest** + **@Testcontainers** for real DB integration tests  
- `@Sql` & custom SQL scripts ensuring consistent test seed data  
- Seamless environment parity for both local development and CI  

### 🚀 **Transactional Repository Classes**
- **AuthorRepositoryImpl, BookRepositoryImpl, LibraryRepositoryImpl** for CRUD operations  
- Fine-tuned `@Transactional(readOnly = true)` queries vs. read-write transactions  
- Merging, updating, and removing entities with explicit session calls  

---

## 🛠️ Built With
- **Spring Framework** - Transaction & Bean management  
- **Hibernate ORM 6** - Advanced entity modeling & HQL queries  
- **Testcontainers** - For reproducible, container-based testing  
- **Gradle** - Build, test, and dependency management  
