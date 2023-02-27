package ru.job4j.cinema.service;

import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.GenreStore;

import java.util.List;

@Data
@Repository
public class GenreService {
    private final GenreStore genreStore;

    public List<Genre> findAll() {
        return genreStore.findAll();
    }

    public Genre findById(int id) {
        return genreStore.findById(id);
    }

    public Genre findByName(String name) {
        return genreStore.findByName(name);
    }
}
