package ru.job4j.cinema.model;

import lombok.Data;

@Data
public class File {
    private int id;
    private String name;
    private String path;
    private byte[] img;
}
