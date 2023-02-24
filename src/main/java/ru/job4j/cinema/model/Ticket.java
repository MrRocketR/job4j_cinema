package ru.job4j.cinema.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
@Data
public class Ticket  implements Serializable {

    private int id;
    private Session session;
    private int row;
    private int place;
    private User user;

}

