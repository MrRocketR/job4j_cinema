create table if not exists FILMS (
    id serial primary key,
    name varchar,
    description varchar,
    poster bytea
);


comment on table films is 'table of films in the cinema';
comment on column films.id is 'film id';
comment on column films.name is 'film title;
comment on column films.description is 'film description';
comment on column films.poster is 'film poster (in bytes)';

