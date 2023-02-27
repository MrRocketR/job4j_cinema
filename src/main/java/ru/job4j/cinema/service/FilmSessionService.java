package ru.job4j.cinema.service;

import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmSessionStore;

import java.util.List;

@Data
@Service
public class FilmSessionService {
    private final FilmSessionStore filmSessionStore;

    public List<FilmSession> getAll() {
        return filmSessionStore.findAll();
    }

    public FilmSession findById(int id) {
        return filmSessionStore.findById(id);
    }
    public List<FilmSession> getSessionsByFilmsId(int filmId) {
        return filmSessionStore.findByFilmId(filmId);
    }

}
