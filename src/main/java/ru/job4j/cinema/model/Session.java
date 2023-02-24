package ru.job4j.cinema.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Session {
    private int id;
    private Film film;
    private Hall hall;
    private Timestamp start;
    private Timestamp end;
    private int price;

}
