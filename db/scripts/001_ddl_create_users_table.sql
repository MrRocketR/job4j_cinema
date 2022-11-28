
create table if not exists USERS (
    id serial primary key,
    username varchar not null unique,
    email VARCHAR NOT NULL UNIQUE,
    phone VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);


comment on table users is 'users';
comment on column users.id is 'User id';
comment on column users.username is 'username;
comment on column users.email is 'user email';
comment on column users.phone is 'user phone';
comment on column users.password is 'user password';

