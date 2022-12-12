package com.example.testproject.controller;

import com.example.testproject.entity.Film;
import com.example.testproject.repository.FilmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("/list")
    public ModelAndView getAllFilms() {
        log.info("/list -> connection");
        ModelAndView mav = new ModelAndView("list-films");
        mav.addObject("films", filmRepository.findAll());
        return mav;
    }

    @GetMapping("/addFilm")
    public ModelAndView addFilm() {
        ModelAndView mav = new ModelAndView("add-film");
        Film film = new Film();
        mav.addObject("film", film);
        return mav;
    }
    @PostMapping("/saveFilm")
    public String saveFilm(@ModelAttribute Film film) {
        filmRepository.save(film);
        return "redirect:/list";
    }
    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long filmId) {
        ModelAndView mav = new ModelAndView("add-film");
        Optional<Film> optionalFilm = filmRepository.findById(filmId);
        Film film = new Film();
        if (optionalFilm.isPresent()) {
            film = optionalFilm.get();
        }
        mav.addObject("film", film);
        return mav;
    }

    @GetMapping("/deleteFilm")
    public String deleteFilm(@RequestParam Long filmId) {
        filmRepository.deleteById(filmId);
        return "redirect:/list";
    }
}
