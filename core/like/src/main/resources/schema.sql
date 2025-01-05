drop table if exists likes;

create table if not exists likes
(
    id         bigint generated always as identity primary key,
    event_id   bigint REFERENCES events (id) ON DELETE CASCADE ON UPDATE CASCADE,
    user_id    bigint REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    status     varchar(8) NOT NULL,
    created    TIMESTAMP
);