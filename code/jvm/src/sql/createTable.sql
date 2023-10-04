drop table if exists match;
drop table if exists token;
drop table if exists "user";
drop table if exists rank;

create table if not exists rank
(
    id       int generated always as identity primary key,
    name     varchar(20) unique not null,
    icon_url TEXT unique        not null,
    min_mmr  int                not null
);

create table if not exists "user"
(
    id         int generated always as identity primary key,
    name       varchar(20),
    email      varchar(200) unique not null,
    password   varchar(30),
    role       varchar(5)          not null check ( role in ('user', 'dev') ) default 'user',
    mmr        int                                                            default 0 not null check ( mmr >= 0 ),
    avatar_url TEXT,
    created_at timestamp           not null                                   default now(),
    rank_id    int references rank (id)
);

create table if not exists token
(
    value      VARCHAR(256) primary key,
    created_at timestamp not null default now(),
    last_used  timestamp not null default now(),
    user_id    int references "user" (id)
);

create table if not exists match
(
    id         uuid               default gen_random_uuid() primary key,
    visibility varchar(7) check ( visibility in ('public', 'private') ),
    board      VARCHAR(256),
    created_at timestamp not null default now(),
    player1_id int references "user" (id),
    player2_id int references "user" (id),
    winner_id  int references "user" (id) check ( winner_id in (player1_id, player2_id) )
);
