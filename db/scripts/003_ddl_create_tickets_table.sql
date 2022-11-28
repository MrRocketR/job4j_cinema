create table if not exists TICKETS (
    id serial primary key,
    row_id INT NOT NULL UNIQUE,
    seat INT NOT NULL UNIQUE,
    user_id int not null references users(id),
    film_id int not null references films(id)
);

comment on table tickets is 'table of bought tickets';
comment on column tickets.id is 'ticket id';
comment on column tickets.row_id is 'selected row';
comment on column tickets.seat is 'selected seat';
comment on column tickets.user_id is 'user id from table users';
comment on column tickets.film_id is 'film id from table films';
