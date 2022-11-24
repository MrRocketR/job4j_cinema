package ru.job4j.cinema.controller;


import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.service.FilmService;

import javax.servlet.http.HttpSession;
import java.io.IOException;



@Controller
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public String films(Model model, HttpSession httpSession) {
        SessionChecker.checkSession(model, httpSession);
        model.addAttribute("films", filmService.findAll());
        return "films";
    }

    @GetMapping("/addFilm")
    public String addFilm(Model model, HttpSession httpSession) {
        SessionChecker.checkSession(model, httpSession);
        return "addFilms";
    }

    @PostMapping("/createFilm")
    public String createFilm(@ModelAttribute Film film, @RequestParam("file") MultipartFile file) throws IOException {
        film.setPoster(file.getBytes());
        filmService.add(film);
        return "redirect:/films";
    }


    @GetMapping("/formUpdateFilm/{film.id}")
    public String formUpdateCandidate(Model model, @PathVariable("film.id") int id, HttpSession httpSession) {
        SessionChecker.checkSession(model, httpSession);
        model.addAttribute("film", filmService.findById(id));
        return "updateFilm";
    }

    @PostMapping("/updateFilm")
    public String updateCandidate(@ModelAttribute Film film, @RequestParam("file") MultipartFile file, HttpSession httpSession) throws IOException {
        film.setPoster(file.getBytes());
        filmService.update(film);
        return "redirect:/films";
    }

    @GetMapping("/filmPoster/{film.id}")
    public ResponseEntity<Resource> download(@PathVariable("film.id") Integer filmId) {
        Film film = filmService.findById(filmId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(film.getPoster().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(film.getPoster()));
    }
}

