package com.bookstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Miguel Castro
 */
@Entity
@Table(name = "publisher")
public class PublisherModel implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;
    
    // relationship - one to many
    /**
     * FetchType.LAZY
     * we will only include the subquery to bring up which books are part of this publisher 
     * when necessary since we are using lazy loading.
     * 
     * FetchType.EAGER
     * it will always look for the publisher in the database and automatically it will also 
     * load all the subqueries to bring up each of the books that are part of that publisher, 
     * we have to be careful when using the 'EAGER' type because we can generate loops in the queries.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    /**
     * we can use List too, but when we work with many relationships within the same entity,
     * if we use List we will have problems loading all these lists,
     * because hibernate cannot bring all these relationships when we define it as List,
     * now when we define it as Set we don't have this problem.
     */
    private Set<BookModel> books = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BookModel> getBooks() {
        return books;
    }

    public void setBooks(Set<BookModel> books) {
        this.books = books;
    }
}
