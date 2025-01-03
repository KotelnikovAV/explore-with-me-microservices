drop table if exists categories;

create table if not exists categories
(
    id   bigint generated always as identity primary key,
    name varchar(100) not null
);