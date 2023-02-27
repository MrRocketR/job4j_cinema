package ru.job4j.cinema.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.FileStore;

import java.util.List;


@Data
@Service
public class FileService {

    private final FileStore fileStore;

    public List<File> findAll() {
        return fileStore.findAll();
    }

    public File findById(int id) {
        return fileStore.findById(id);
    }

    public File findByName(String name) {
        return fileStore.findByName(name);
    }

}
