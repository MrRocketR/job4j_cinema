package ru.job4j.cinema.service;

import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class FilmDtoService {

    private final FilmService filmService;
    private final GenreService genreService;
    private final FileService fileService;


    public FilmDto findFilmById(int id) {
        Film film = filmService.findById(id);
        FilmDto filmDto = buildDtoFilms(film);
        return filmDto;
    }

    private FilmDto buildDtoFilms(Film film) {
        return FilmDto.
                builder().id(film.getId()).
                name(film.getName()).description(film.getDescription())
                .year(film.getYear()).durationInMinutes(film.getDuration())
                .minimalAge(film.getAge())
                .genre(genreService.findById(film.getId()).getName())
                .poster(fileService.findById(film.getFileId()).getImg()).
                build();
    }

    public List<FilmDto> findAll() {
        List<Film> films = filmService.findAll();
        List<FilmDto> filmDtoList = new ArrayList<>();
        for (Film f : films) {
            filmDtoList.add(buildDtoFilms(f));
        }
        return filmDtoList;
    }

}
