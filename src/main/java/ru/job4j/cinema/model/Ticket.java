package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket  implements Serializable {

    private int id;
    private int sessionId;
    private int row;
    private int place;
    private int userId;

}

