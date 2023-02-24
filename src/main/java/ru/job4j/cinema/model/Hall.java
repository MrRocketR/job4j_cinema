package ru.job4j.cinema.model;

import lombok.Data;

@Data
public class Hall {
    private int id;
    private String name;
    private int rows;
    private int places;
    private String description;
}
