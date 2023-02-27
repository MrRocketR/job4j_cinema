package ru.job4j.cinema.repository;

import java.util.List;

public interface RepoAllNameId<T> {
    public List<T> findAll();

    public T findById(int id);

    public T findByName(String name);


}
