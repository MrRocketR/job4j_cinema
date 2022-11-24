create table if not exists FILMS (
    id serial primary key,
    name varchar,
    description varchar,
    poster bytea
);