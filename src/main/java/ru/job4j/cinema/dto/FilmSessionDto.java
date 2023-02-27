package ru.job4j.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmSessionDto {
    private int id;
    private Timestamp start;
    private Timestamp end;
    private int price;
    private int hallId;
    /**
    From Film Model
     **/
    private String film;
    private int filmId;

    /**
     from Hall model
     */
    private String hall;
}
