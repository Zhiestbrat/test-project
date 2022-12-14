package com.example.testproject.controller;

import com.example.testproject.entity.Film;
import com.example.testproject.entity.Log;
import com.example.testproject.entity.User;
import com.example.testproject.repository.FilmRepository;
import com.example.testproject.repository.LogRepository;
import com.example.testproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.util.Date;
import java.util.Optional;

@Slf4j
@Controller
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogRepository logRepository;

    @GetMapping("/list")
    public ModelAndView getAllFilms(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        log.info("/list -> connection");
        ModelAndView mav = new ModelAndView("list-films");
        mav.addObject("user", userRepository.findByEmail(authentication.getName()));
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
    public String saveFilm(@ModelAttribute Film film, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        film.setUser(user);
        filmRepository.save(film);

        Log log = new Log();
        log.setUser(user);
        log.setDate(new Date());
        log.setDescription("saveFilm " + film);
        logRepository.save(log);
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
    public String deleteFilm(@RequestParam Long filmId, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        Log log = new Log();
        log.setUser(user);
        log.setDate(new Date());
        log.setDescription("deleteFilmId " + filmId);
        logRepository.save(log);
        filmRepository.deleteById(filmId);
        return "redirect:/list";
    }
}
