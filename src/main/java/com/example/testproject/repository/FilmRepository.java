package com.example.testproject.repository;

import com.example.testproject.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository  extends JpaRepository<Film, Long> {
}
