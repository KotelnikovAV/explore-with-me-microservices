drop table if exists event_similarity, user_action;

create table if not exists event_similarity
(
    id          bigint              GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_A     bigint              NOT NULL,
    event_B     bigint              NOT NULL,
    score       double precision    NOT NULL
);

create table if not exists user_action
(
    id              bigint              GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id         bigint              NOT NULL,
    event_id        bigint              NOT NULL,
    action_type     varchar(10)         NOT NULL,
    action_date     timestamp           NOT NULL
);