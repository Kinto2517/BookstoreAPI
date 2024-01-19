package com.kinto2517.bookstoreapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique=true)
    private String title;

    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant publishDate;

    @ManyToOne
    @JoinColumn(name = "bookstore_id")
    @JsonBackReference
    private Bookstore bookstore;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Borrow> borrowings;


}

