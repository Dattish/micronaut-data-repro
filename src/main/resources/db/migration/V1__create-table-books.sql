create table books
(
    id     uuid primary key,
    author uuid    not null,
    name   text    not null,
    read   boolean not null
);