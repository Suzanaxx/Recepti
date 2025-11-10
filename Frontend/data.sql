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

INSERT INTO recepti (idje, ime, opis, sestavine, navodila, slika, kalorije, proteini, karbohidrati, mascobe, vlaknine)
VALUES
-- 10–30: dodatni podvojeni recepti z različnimi imeni
('RCP011', 'Tuna sendvič', 'Hiter sendvič s tuno in zelenjavo.', 
 'tunina, kruh, solata, majoneza', 'Zmešaj sestavine, namaži na kruh.', 
 '/images/chili_con_carne.jpg', 350, 20, 30, 10, 3),
('RCP012', 'Jabolčni zavitek', 'Sladek zavitek z jabolki in cimetom.', 
 'jabolka, listnato testo, sladkor, cimet', 'Nadev zavij v testo, peci 25 minut pri 180 °C.', 
 '/images/cokoladna_torta.jpg', 400, 5, 50, 15, 2),
('RCP013', 'Grill piščanec', 'Piščančje prsi na žaru z začimbami.', 
 'piščanec, paprika, sol, poper', 'Začini piščanca in peci na žaru 20 minut.', 
 '/images/pecen_piscanc.jpg', 360, 32, 2, 18, 1),
('RCP014', 'Veggie burger', 'Zelenjavni burger z avokadom in solato.', 
 'burger žemlja, avokado, solata, paradižnik, fižol', 'Sestavi burger in postrezi.', 
 '/images/pesto_testenine.jpg', 420, 14, 50, 15, 4),
('RCP015', 'Špageti Bolognese', 'Kremasti špageti z mesno omako.', 
 'špageti, mleto meso, paradižnik, česen, čebula, bazilika', 'Skuhaj špagete in omako, zmešaj skupaj.', 
 '/images/spageti_carbonara.jpg', 500, 25, 60, 18, 2),
('RCP016', 'Smoothie', 'Zdrav sadni smoothie za zajtrk.', 
 'banana, jagode, jogurt, med', 'Vse sestavine zmešaj v blenderju.', 
 '/images/zelenjavna_juha.jpg', 220, 5, 40, 2, 3),
('RCP017', 'Omleta', 'Hitra omleta z zelenjavo.', 
 'jajca, paprika, paradižnik, sol, poper', 'Stepaj jajca in specite z zelenjavo na ponvi.', 
 '/images/palacinke.jpg', 250, 12, 5, 18, 1),
('RCP018', 'Minestrone', 'Kremasta italijanska zelenjavna juha.', 
 'korenje, krompir, por, bučke, fižol, paradižnik', 'Skuhaj vse sestavine v vodi 30 minut.', 
 '/images/rizota.jpg', 200, 7, 25, 5, 5),
('RCP019', 'Lasagna', 'Klasčna lazanja z mesom in sirom.', 
 'testenine, mleto meso, paradižnik, sir, bešamel', 'Sestavi sloje in peci 45 minut pri 180 °C.', 
 '/images/pizza_margherita.jpg', 600, 28, 50, 25, 3),
('RCP020', 'Caprese solata', 'Solata z mocarelo, paradižnikom in baziliko.', 
 'paradižnik, mocarela, bazilika, olivno olje', 'Sestavi sestavine na krožnik in pokapaj z oljem.', 
 '/images/pesto_testenine.jpg', 300, 12, 10, 20, 2),

-- 21–50: hitro podvojimo prejšnje recepte s spremembo idje in imena
('RCP021', 'Chili con carne 2', 'Pikantna mehiška jed z mletim mesom.', 
 'mleto meso, fižol, paradižnik', 'Praži čebulo, dodaj meso in fižol.', 
 '/images/chili_con_carne.jpg', 420, 28, 30, 18, 6),
('RCP022', 'Čokoladna torta 2', 'Sočna torta z bogato čokoladno kremo.', 
 'jajca, moka, sladkor, kakav', 'Zmešaj sestavine, speci biskvit.', 
 '/images/cokoladna_torta.jpg', 520, 8, 60, 28, 2),
('RCP023', 'Losos na žaru 2', 'Sočen losos z limoninim prelivom.', 
 'losos, limona, olivno olje', 'Začini lososa, speci 5 minut.', 
 '/images/losos_na_ziaru.jpg', 350, 32, 2, 20, 0),
('RCP024', 'Palačinke 2', 'Klasične mehke palačinke.', 
 'moka, mleko, jajca', 'Zmešaj sestavine, specite palačinke.', 
 '/images/palacinke.jpg', 280, 7, 35, 12, 1),
('RCP025', 'Pečen piščanec 2', 'Hrustljav piščanec pečen v pečici.', 
 'piščanec, česen, limona', 'Začini piščanca, peci 1 uro.', 
 '/images/pecen_piscanc.jpg', 410, 36, 5, 26, 0),
('RCP026', 'Pesto testenine 2', 'Testenine z bazilikinim pestom.', 
 'testenine, bazilika, parmezan', 'Skuhaj testenine, dodaj pesto.', 
 '/images/pesto_testenine.jpg', 470, 14, 50, 22, 3),
('RCP027', 'Pizza Margherita 2', 'Klasična italijanska pizza.', 
 'moka, kvas, voda, paradižnik, sir', 'Zamesi testo, peci 10 minut.', 
 '/images/pizza_margherita.jpg', 590, 22, 65, 20, 3),
('RCP028', 'Rižota 2', 'Kremasta rižota z zelenjavo.', 
 'riž, čebula, vino', 'Praži riž, dodaj tekočino.', 
 '/images/rizota.jpg', 430, 10, 55, 14, 2),
('RCP029', 'Špageti Carbonara 2', 'Prava carbonara s panceto.', 
 'špageti, jajca, panceta', 'Skuhaj špagete, zmešaj z jajci.', 
 '/images/spageti_carbonara.jpg', 520, 24, 45, 25, 1),
('RCP030', 'Zelenjavna juha 2', 'Lahka zelenjavna juha.', 
 'korenje, krompir, por', 'Skuhaj zelenjavo v vodi.', 
 '/images/zelenjavna_juha.jpg', 180, 6, 20, 5, 4),

('RCP031', 'Tuna sendvič 2', 'Hiter sendvič s tuno.', 
 'tunina, kruh, solata', 'Zmešaj sestavine, namaži na kruh.', 
 '/images/chili_con_carne.jpg', 350, 20, 30, 10, 3),
('RCP032', 'Jabolčni zavitek 2', 'Sladek zavitek z jabolki.', 
 'jabolka, listnato testo', 'Nadev zavij v testo, peci 25 minut.', 
 '/images/cokoladna_torta.jpg', 400, 5, 50, 15, 2),
('RCP033', 'Grill piščanec 2', 'Piščančje prsi na žaru.', 
 'piščanec, paprika, sol', 'Začini piščanca, peci 20 minut.', 
 '/images/pecen_piscanc.jpg', 360, 32, 2, 18, 1),
('RCP034', 'Veggie burger 2', 'Zelenjavni burger.', 
 'burger žemlja, avokado, solata', 'Sestavi burger in postrezi.', 
 '/images/pesto_testenine.jpg', 420, 14, 50, 15, 4),
('RCP035', 'Špageti Bolognese 2', 'Kremasti špageti.', 
 'špageti, mleto meso', 'Skuhaj špagete in omako.', 
 '/images/spageti_carbonara.jpg', 500, 25, 60, 18, 2),
('RCP036', 'Smoothie 2', 'Sadni smoothie.', 
 'banana, jagode, jogurt', 'Vse sestavine zmešaj v blenderju.', 
 '/images/zelenjavna_juha.jpg', 220, 5, 40, 2, 3),
('RCP037', 'Omleta 2', 'Hitra omleta.', 
 'jajca, paprika', 'Stepaj jajca in specite z zelenjavo.', 
 '/images/palacinke.jpg', 250, 12, 5, 18, 1),
('RCP038', 'Minestrone 2', 'Italijanska zelenjavna juha.', 
 'korenje, krompir, bučke', 'Skuhaj vse sestavine v vodi.', 
 '/images/rizota.jpg', 200, 7, 25, 5, 5),
('RCP039', 'Lasagna 2', 'Klasčna lazanja.', 
 'testenine, mleto meso, sir', 'Sestavi sloje in peci 45 minut.', 
 '/images/pizza_margherita.jpg', 600, 28, 50, 25, 3),
('RCP040', 'Caprese solata 2', 'Solata z mocarelo.', 
 'paradižnik, mocarela, bazilika', 'Sestavi sestavine in pokapaj z oljem.', 
 '/images/pesto_testenine.jpg', 300, 12, 10, 20, 2),

('RCP041', 'Chili con carne 3', 'Pikantna jed z mletim mesom.', 
 'mleto meso, fižol', 'Praži čebulo, dodaj meso in fižol.', 
 '/images/chili_con_carne.jpg', 420, 28, 30, 18, 6),
('RCP042', 'Čokoladna torta 3', 'Sočna torta.', 
 'jajca, moka, kakav', 'Zmešaj sestavine, speci biskvit.', 
 '/images/cokoladna_torta.jpg', 520, 8, 60, 28, 2),
('RCP043', 'Losos na žaru 3', 'Sočen losos.', 
 'losos, limona', 'Začini lososa, speci 5 minut.', 
 '/images/losos_na_ziaru.jpg', 350, 32, 2, 20, 0),
('RCP044', 'Palačinke 3', 'Mehke palačinke.', 
 'moka, mleko, jajca', 'Zmešaj sestavine, specite palačinke.', 
 '/images/palacinke.jpg', 280, 7, 35, 12, 1),
('RCP045', 'Pečen piščanec 3', 'Hrustljav piščanec.', 
 'piščanec, česen', 'Začini piščanca, peci 1 uro.', 
 '/images/pecen_piscanc.jpg', 410, 36, 5, 26, 0),
('RCP046', 'Pesto testenine 3', 'Testenine z bazilikinim pestom.', 
 'testenine, bazilika', 'Skuhaj testenine, dodaj pesto.', 
 '/images/pesto_testenine.jpg', 470, 14, 50, 22, 3),
('RCP047', 'Pizza Margherita 3', 'Italijanska pizza.', 
 'moka, kvas, voda, paradižnik', 'Zamesi testo, peci 10 minut.', 
 '/images/pizza_margherita.jpg', 590, 22, 65, 20, 3),
('RCP048', 'Rižota 3', 'Kremasta rižota.', 
 'riž, čebula', 'Praži riž, dodaj tekočino.', 
 '/images/rizota.jpg', 430, 10, 55, 14, 2),
('RCP049', 'Špageti Carbonara 3', 'Carbonara s panceto.', 
 'špageti, jajca', 'Skuhaj špagete, zmešaj z jajci.', 
 '/images/spageti_carbonara.jpg', 520, 24, 45, 25, 1),
('RCP050', 'Zelenjavna juha 3', 'Lahka juha.', 
 'korenje, krompir', 'Skuhaj zelenjavo v vodi.', 
 '/images/zelenjavna_juha.jpg', 180, 6, 20, 5, 4);