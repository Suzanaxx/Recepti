USE data;

create table if not exists recepti
(
    id           int unsigned auto_increment
        primary key,
    idje         varchar(255) not null,
    ime          varchar(255) not null,
    opis         varchar(255) null,
    sestavine    varchar(255) null,
    navodila     varchar(255) null,
    slika        varchar(255) null,
    kalorije     double       null,
    proteini     double       null,
    karbohidrati double       null,
    mascobe      double       null,
    vlaknine     double       null,
    constraint idje
        unique (idje)
);

create table if not exists users
(
    id         int unsigned auto_increment
        primary key,
    username   varchar(50)                         not null,
    email      varchar(100)                        not null,
    password   varchar(255)                        not null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    constraint email
        unique (email),
    constraint username
        unique (username)
);

create table if not exists bookmarks
(
    id         int unsigned auto_increment
        primary key,
    recipe_id  int unsigned                        not null,
    user_id    int unsigned                        not null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    constraint bookmarks_ibfk_1
        foreign key (recipe_id) references recepti (id)
            on delete cascade,
    constraint bookmarks_ibfk_2
        foreign key (user_id) references users (id)
            on delete cascade
);

create index recipe_id
    on bookmarks (recipe_id);

create index user_id
    on bookmarks (user_id);

create table if not exists comments
(
    id         bigint auto_increment
        primary key,
    recipe_id  int unsigned                        not null,
    user_id    int unsigned                        not null,
    comment    varchar(255)                        not null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    constraint comments_ibfk_1
        foreign key (recipe_id) references recepti (id)
            on delete cascade,
    constraint comments_ibfk_2
        foreign key (user_id) references users (id)
            on delete cascade
);

create index recipe_id
    on comments (recipe_id);

create index user_id
    on comments (user_id);

create table if not exists ratings
(
    id         bigint auto_increment
        primary key,
    recipe_id  int unsigned                        not null,
    user_id    int unsigned                        not null,
    rating     int                                 not null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    constraint ratings_ibfk_1
        foreign key (recipe_id) references recepti (id)
            on delete cascade,
    constraint ratings_ibfk_2
        foreign key (user_id) references users (id)
            on delete cascade,
    check (`rating` between 1 and 5)
);

create index recipe_id
    on ratings (recipe_id);

create index user_id
    on ratings (user_id);

