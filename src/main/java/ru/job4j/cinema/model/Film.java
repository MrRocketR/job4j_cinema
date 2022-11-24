package ru.job4j.cinema.model;

import java.util.Arrays;
import java.util.Objects;

public class Film {
    private int id;
    private String name;
    private String description;
    private byte[] poster;

    public Film(int id, String name, String description, byte[] poster) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.poster = poster;
    }

    public Film() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass())  {
            return false;
        }
        Film film = (Film) o;
        return id == film.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Film{" + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", poster="  + '}';
    }
}
