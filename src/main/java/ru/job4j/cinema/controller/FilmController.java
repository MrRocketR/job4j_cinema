package ru.job4j.cinema.controller;


import lombok.Data;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmDtoService;
import javax.servlet.http.HttpSession;


@Controller
@Data
public class FilmController {

    private final FilmDtoService filmService;


    @GetMapping("/films")
    public String films(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        model.addAttribute("user", user);
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


