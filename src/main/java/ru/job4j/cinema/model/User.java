package ru.job4j.cinema.model;

import lombok.Data;

import java.util.Objects;
@Data
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
}
