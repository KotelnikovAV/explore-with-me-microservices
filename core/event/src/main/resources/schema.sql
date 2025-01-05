drop table if exists location, events;

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