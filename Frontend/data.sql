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

USE data;

INSERT INTO recepti (idje, ime, opis, sestavine, navodila, slika, kalorije, proteini, karbohidrati, mascobe, vlaknine)
VALUES
('RCP001', 'Chili con carne', 'Pikantna mehiška jed z mletim mesom, fižolom in papriko.', 
 'mleto meso, fižol, paradižnik, čebula, česen, paprika, kumina', 
 'Praži čebulo in česen, dodaj meso, nato fižol in paradižnik. Kuhaj 30 minut.', 
 '/images/chili_con_carne.jpg', 420, 28, 30, 18, 6),

('RCP002', 'Čokoladna torta', 'Sočna torta z bogato čokoladno kremo.', 
 'jajca, moka, sladkor, kakav, čokolada, maslo', 
 'Zmešaj sestavine, speci biskvit, dodaj kremo in ohladi.', 
 '/images/cokoladna_torta.jpg', 520, 8, 60, 28, 2),

('RCP003', 'Losos na žaru', 'Sočen losos z limoninim prelivom in zelišči.', 
 'losos, limona, olivno olje, koper, sol, poper', 
 'Začini lososa, speci na žaru 5–7 minut z vsake strani.', 
 '/images/losos_na_ziaru.jpg', 350, 32, 2, 20, 0),

('RCP004', 'Palačinke', 'Klasične mehke palačinke, odlične za zajtrk.', 
 'moka, mleko, jajca, sladkor, olje, sol', 
 'Zmešaj sestavine, speci tanke palačinke na ponvi.', 
 '/images/palacinke.jpg', 280, 7, 35, 12, 1),

('RCP005', 'Pečen piščanec', 'Hrustljav piščanec pečen v pečici z začimbami.', 
 'piščanec, česen, limona, olivno olje, timijan, sol, poper', 
 'Začini piščanca, peci 1 uro pri 200 °C.', 
 '/images/pecen_piscanc.jpg', 410, 36, 5, 26, 0),

('RCP006', 'Pesto testenine', 'Testenine z bazilikinim pestom in parmezanom.', 
 'testenine, bazilika, pinjole, česen, parmezan, olivno olje', 
 'Skuhaj testenine, prelij s pestom in dodaj sir.', 
 '/images/pesto_testenine.jpg', 470, 14, 50, 22, 3),

('RCP007', 'Pizza Margherita', 'Klasična italijanska pizza s sirom in paradižnikom.', 
 'moka, kvas, voda, paradižnik, mocarela, bazilika', 
 'Zamesi testo, dodaj nadev in peci 10 minut pri 250 °C.', 
 '/images/pizza_margherita.jpg', 590, 22, 65, 20, 3),

('RCP008', 'Rižota', 'Kremasta rižota z zelenjavo in parmezanom.', 
 'riž, čebula, vino, jušna osnova, sir, maslo', 
 'Praži riž, dodaj tekočino postopoma, mešaj do kremaste teksture.', 
 '/images/rizota.jpg', 430, 10, 55, 14, 2),

('RCP009', 'Špageti Carbonara', 'Prava italijanska carbonara s panceto in parmezanom.', 
 'špageti, jajca, panceta, parmezan, poper, sol', 
 'Skuhaj špagete, zmešaj z jajci in panceto, postrezi takoj.', 
 '/images/spageti_carbonara.jpg', 520, 24, 45, 25, 1),

('RCP010', 'Zelenjavna juha', 'Lahka in zdrava juha z mešano zelenjavo.', 
 'korenje, krompir, por, grah, bučke, sol, poper', 
 'Skuhaj zelenjavo v vodi, začini po okusu.', 
 '/images/zelenjavna_juha.jpg', 180, 6, 20, 5, 4);
