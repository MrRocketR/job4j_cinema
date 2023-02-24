package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Genre;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RepoAllNameId <T> {
    public List<T> getAll();
    public T getById(int id);
    public T getByName(String name);



}
