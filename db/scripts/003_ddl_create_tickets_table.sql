create table if not exists TICKETS (
    id serial primary key,
    row_id INT NOT NULL UNIQUE,
    seat INT NOT NULL UNIQUE,
    user_id int not null references users(id),
    film_id int not null references films(id)
);
