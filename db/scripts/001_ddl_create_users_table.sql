
create table if not exists USERS (
    id serial primary key,
    username varchar not null unique,
    email VARCHAR NOT NULL UNIQUE,
    phone VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);




