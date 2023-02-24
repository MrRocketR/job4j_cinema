package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hall {
    private int id;
    private String name;
    private int rows;
    private int places;
    private String description;

    /**
     пока под вопросов этого поля
     */
    private Map<Integer, List<Integer>> freePlaces;
}
