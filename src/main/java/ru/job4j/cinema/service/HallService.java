package ru.job4j.cinema.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallStore;

import java.util.List;

@Data
@Service
public class HallService {

    private final HallStore hallStore;

    public List<Hall> findAll() {
        return hallStore.findAll();
    }
    public Hall findById(int id) {
        return hallStore.findById(id);
    }

    public Hall findByName(String name) {
        return hallStore.findByName(name);
    }

}
