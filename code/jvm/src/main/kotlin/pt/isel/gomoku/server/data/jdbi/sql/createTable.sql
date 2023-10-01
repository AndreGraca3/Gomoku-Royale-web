drop table play;
drop table match;
drop table userToken;
drop table users;

create table users
(
    id            serial primary key,
    name          varchar(20),
    email         varchar(200),
    password      varchar(30),
    avatar        TEXT,
    Rank          int default 0,
    numberOfGames int default 0
);

create table userToken
(
    idUser      int primary key,
    token       TEXT,
    initialTime timestamp,
    lifeTime    timestamp,
    foreign key (idUser) references users (id)
);

create table match
(
    id       serial primary key,
    variants varchar(20),
    board    JSON
);

create table play
(
    idUser  int,
    idMatch int,
    color   char(1),
    primary key (idUser, idMatch),
    foreign key (idUser) references users (id),
    foreign key (idMatch) references match (id)
);

insert into match (variants, board)
values ('1', '{
                "moves" : [],
                "turn" : {
                    "Player": "BLACK"
                }
           }');

select board from match where id = 2;