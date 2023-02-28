create table film_sessions
(
    id         serial primary key,
    film_id      int not null references films (id),
    halls_id     int not null references halls (id),
    start_time timestamp                 not null,
    end_time   timestamp                 not null,
    price      int                       not null
);