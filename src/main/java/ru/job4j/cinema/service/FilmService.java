package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmStore;
import java.util.Collection;

@Service
public class FilmService {
    private final FilmStore store;

    public FilmService(FilmStore store) {
        this.store = store;
    }

    public Collection<Film> findAll() {
        Collection<Film> films = store.findAll();
        return films;
    }

    public void add(Film film) {
        store.add(film);
    }

    public Film findById(int id) {
        return store.findById(id);
    }


    public void update(Film film) {
        store.update(film);
    }

}
