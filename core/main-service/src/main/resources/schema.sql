drop table if exists users, categories, location, events, requests, compilations, compilations_events, likes;

create table if not exists users
(
    id      bigint generated always as identity primary key,
    email   varchar(255) not null unique,
    name    varchar(255) not null,
    rating  INTEGER not null
);

create table if not exists categories
(
    id   bigint generated always as identity primary key,
    name varchar(100) not null
);

create table if not exists location
(
    id  bigint generated always as identity primary key,
    lat float not null,
    lon float not null
);

create table if not exists events
(
    id                 bigint generated always as identity primary key,
    annotation         varchar,
    category_id        bigint REFERENCES categories (id) ON DELETE CASCADE ON UPDATE CASCADE,
    created_on         TIMESTAMP NOT NULL,
    description        varchar   NOT NULL,
    event_date         TIMESTAMP NOT NULL,
    initiator_id       bigint REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    location_id        bigint REFERENCES location (id) ON DELETE CASCADE ON UPDATE CASCADE,
    paid               BOOLEAN   NOT NULL,
    participant_limit  INTEGER   NOT NULL,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN   NOT NULL,
    state              varchar   NOT NULL,
    title              varchar   NOT NULL,
    confirmed_requests INTEGER   NOT NULL,
    rating             INTEGER   NOT NULL
);

create table if not exists requests
(
    id           bigint generated always as identity primary key,
    created      TIMESTAMP   not null,
    event_id     bigint REFERENCES events (id) ON DELETE CASCADE ON UPDATE CASCADE,
    requester_id bigint REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    status       varchar(20) NOT NULL
);

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

create table if not exists likes
(
    id         bigint generated always as identity primary key,
    event_id   bigint REFERENCES events (id) ON DELETE CASCADE ON UPDATE CASCADE,
    user_id    bigint REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    status     varchar(8) NOT NULL,
    created    TIMESTAMP
);