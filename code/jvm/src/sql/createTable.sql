drop table if exists board;
drop table if exists match;
drop table if exists token;
drop table if exists "user";
drop table if exists rank;

create table if not exists rank
(
    name     varchar(20) primary key,
    icon_url TEXT unique not null,
    min_mmr  int         not null
);

create table if not exists "user"
(
    id         int generated always as identity primary key,
    name       varchar(20),
    email      varchar(200) unique not null,
    password   varchar(30),
    role       varchar(5)          not null check ( role in ('user', 'admin') ) default 'user',
    mmr        int                                                              default 0 not null check ( mmr >= 0 ),
    avatar_url TEXT,
    created_at timestamp           not null                                     default now(),
    rank       varchar(20)         not null references rank (name)              default 'Bronze'
);

create table if not exists token
(
    token_value VARCHAR(256) primary key,
    created_at  timestamp not null default now(),
    last_used   timestamp not null default now(),
    user_id     int references "user" (id)
);

create table if not exists match
(
    id         VARCHAR(256) primary key                                                                default gen_random_uuid(),
    isPrivate  Boolean                    not null,
    variant    VARCHAR(20)                not null,
    created_at timestamp                  not null                                                     default now(),
    black_id   int references "user" (id) not null,
    white_id   int references "user" (id) check ( white_id <> black_id ),
    state      varchar(20)                not null check ( state in ('SETUP', 'ONGOING', 'FINISHED') ) default 'SETUP'
);

create table if not exists board
(
    match_id VARCHAR(256) primary key references match (id),
    size     int          not null,
    type     varchar(20)  not null,
    stones   varchar(256) not null,
    turn     varchar(1)   not null
);