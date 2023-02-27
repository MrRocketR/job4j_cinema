package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmSession {
    private int id;
    private int filmId;
    private int hallId;
    private Timestamp start;
    private Timestamp end;
    private int price;

}
