package ru.job4j.cinema.controller;


import lombok.Data;
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
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.service.FilmDtoService;
import ru.job4j.cinema.service.FilmService;

import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
@Data
public class FilmController {

    private final FilmDtoService filmService;


    @GetMapping("/films")
    public String films(Model model, HttpSession httpSession) {
        SessionChecker.checkSession(model, httpSession);
        model.addAttribute("films", filmService.findAll());
        return "films";
    }

    @GetMapping("/filmPoster/{film.id}")
    public ResponseEntity<Resource> download(@PathVariable("film.id") Integer filmId) {
        FilmDto film = filmService.findFilmById(filmId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(film.getPoster().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(film.getPoster()));
    }

}


