package ru.job4j.cinema.service;

import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmSessionStore;

import java.util.ArrayList;
import java.util.List;


@Service
@Data
public class FilmSessionDtoService {
    private final FilmSessionService filmSessionService;
    private final HallService hallService;
    private final FilmService filmService;


    public List<FilmSessionDto> findAll() {
        List<FilmSession> list = filmSessionService.getAll();
        List<FilmSessionDto> dtoList = new ArrayList<>();
        for (FilmSession fs : list) {
            dtoList.add(buildDto(fs));
        }
        return dtoList;
    }


    public FilmSessionDto findById(int id) {
        FilmSession filmSession = filmSessionService.findById(id);
        return buildDto(filmSession);
    }

    public List<FilmSessionDto> findByIdFilmID(int filmId) {
        List<FilmSession> list = filmSessionService.getSessionsByFilmsId(filmId);
        List<FilmSessionDto> dtoList = new ArrayList<>();
        for (FilmSession fs : list) {
            dtoList.add(buildDto(fs));
        }
        return dtoList;
    }

    private FilmSessionDto buildDto(FilmSession fs) {
        return FilmSessionDto.builder().id(fs.getId()).start(fs.getStart())
                .end(fs.getEnd()).price(fs.getPrice())
                .film(filmService.findById(fs.getFilmId()).getName())
                .filmId(fs.getFilmId())
                .hallId(fs.getHallId())
                .hall(hallService.findById(fs.getHallId()).getName()).build();

    }



}
