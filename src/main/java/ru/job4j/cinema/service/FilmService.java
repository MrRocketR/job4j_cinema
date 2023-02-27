package ru.job4j.cinema.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmStore;

import java.util.List;


@Data
@Service
public class FilmService {
    private final FilmStore filmStore;


    public List<Film> findAll() {
        return filmStore.findAll();
    }

    public Film findById(int id) {
        return filmStore.findById(id);
    }

    public Film findByName(String name) {
        return filmStore.findByName(name);
    }


}
