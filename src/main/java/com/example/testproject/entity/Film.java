package com.example.testproject.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FILMS")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "FilmName")
    private String name;

    @Column(name = "FilmGenre")
    private String genre;

    @Column(name = "FilmCountry")
    private String country;

    @Column(name = "FilmYear")
    private int year;
}
