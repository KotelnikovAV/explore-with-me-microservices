drop table if exists requests;

create table if not exists requests
(
    id           bigint generated always as identity primary key,
    created      TIMESTAMP   not null,
    event_id     bigint REFERENCES events (id) ON DELETE CASCADE ON UPDATE CASCADE,
    requester_id bigint REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    status       varchar(20) NOT NULL
);