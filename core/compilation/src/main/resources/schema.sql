drop table if exists compilations, compilations_events;

create table if not exists compilations
(
    id     bigint generated always as identity primary key,
    pinned boolean      not null,
    title  varchar(255) not null
);

create table if not exists compilations_events
(
    compilation_id bigint references compilations (id),
    event_id       bigint references events (id)
);