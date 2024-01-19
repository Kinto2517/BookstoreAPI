package com.kinto2517.bookstoreapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookstores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bookstore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique=true)
    private String name;

    private String address;

    private String phoneNumber;

    @OneToMany(mappedBy = "bookstore", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();
}
