package ru.job4j.cinema.model;

import lombok.Data;


@Data
public class Film {
    private int id;
    private String name;
    private String description;
    private int year;
    private Genre genre;
    private int age;
    private int duration;
    private File file;

}
