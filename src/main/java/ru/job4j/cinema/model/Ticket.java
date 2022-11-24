package ru.job4j.cinema.model;

import java.io.Serializable;
import java.util.Objects;

public class Ticket  implements Serializable {

    private int id;
    private int rowId;
    private int seatId;
    private int userId;
    private int filmId;

    public Ticket(int id, int rowId, int seatId, int userId, int filmId) {
        this.id = id;
        this.rowId = rowId;
        this.seatId = seatId;
        this.userId = userId;
        this.filmId = filmId;
    }

    public Ticket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ticket{" + "id=" + id + ", row="
                + rowId + ", cell=" + seatId
                + ", userId=" + userId
                + ", filmId=" + filmId + '}';
    }
}

