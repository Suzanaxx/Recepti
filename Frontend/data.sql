-- Uporaba baze
USE data;

-- Brisanje obstoječih tabel, če obstajajo
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS ratings;
DROP TABLE IF EXISTS bookmarks;
DROP TABLE IF EXISTS recepti;
DROP TABLE IF EXISTS users;

-- Tabela za uporabnike
CREATE TABLE users (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- Primarni ključ (INT UNSIGNED za skladnost)
    username VARCHAR(50) NOT NULL UNIQUE,            -- Unikatno uporabniško ime
    email VARCHAR(100) NOT NULL UNIQUE,              -- Unikaten email
    password VARCHAR(255) NOT NULL,                  -- Geslo (hashirano)
);

-- Tabela za recepte
CREATE TABLE recepti (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- Primarni ključ (INT UNSIGNED za skladnost)
    idje VARCHAR(20) NOT NULL UNIQUE,                -- Unikatna oznaka recepta
    ime VARCHAR(255) NOT NULL,                       -- Ime recepta
    opis TEXT,                                       -- Kratek opis recepta
    sestavine TEXT,                                  -- Sestavine recepta
    navodila TEXT,                                   -- Navodila za pripravo recepta
    slika VARCHAR(255)                               -- URL slike recepta
);

-- Tabela za komentarje
CREATE TABLE comments (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- Primarni ključ
    recipe_id INT UNSIGNED NOT NULL,                  -- Tuji ključ na tabelo recepti
    user_id INT UNSIGNED NOT NULL,                    -- Tuji ključ na tabelo users
    comment TEXT NOT NULL,                            -- Besedilo komentarja
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- Datum komentarja
    FOREIGN KEY (recipe_id) REFERENCES recepti(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela za ocene
CREATE TABLE ratings (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- Primarni ključ
    recipe_id INT UNSIGNED NOT NULL,                  -- Tuji ključ na tabelo recepti
    user_id INT UNSIGNED NOT NULL,                    -- Tuji ključ na tabelo users
    rating TINYINT UNSIGNED CHECK (rating BETWEEN 1 AND 5), -- Ocena (1-5)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- Datum ocene
    FOREIGN KEY (recipe_id) REFERENCES recepti(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela za priljubljene recepte
CREATE TABLE bookmarks (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- Primarni ključ
    recipe_id INT UNSIGNED NOT NULL,                  -- Tuji ključ na tabelo recepti
    user_id INT UNSIGNED NOT NULL,                    -- Tuji ključ na tabelo users
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- Datum dodajanja
    FOREIGN KEY (recipe_id) REFERENCES recepti(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Dodajanje testnih podatkov za uporabnike
INSERT INTO users (username, email, password) VALUES
('testuser1', 'testuser1@example.com', 'hashedpassword1'),
('testuser2', 'testuser2@example.com', 'hashedpassword2'),
('testuser3', 'testuser3@example.com', 'hashedpassword3');

-- Dodajanje testnih receptov
INSERT INTO recepti (idje, ime, opis, sestavine, navodila, slika) VALUES 
('RCP-001', 'Palačinke', 'Okusne palačinke z marmelado ali čokolado.', 'Moka, Mleko, Jajca, Sol, Sladkor', 'Zmešaj vse sestavine, speči na ponvi, dodaj marmelado ali čokolado.', 'images/palacinke.jpg'),
('RCP-002', 'Rižota', 'Klasična rižota s piščancem in zelenjavo.', 'Riž, Piščanec, Paprika, Čebula, Paradižnik, Česen', 'Prepraži čebulo in česen, dodaj piščanca in zelenjav, nato dodaj riž in kuhaj.', 'images/rizota.jpg'),
('RCP-003', 'Čokoladna torta', 'Bogata čokoladna torta za ljubitelje sladkarij.', 'Moka, Sladkor, Čokolada, Maslo, Jajca', 'Zmešaj vse sestavine in peci v pečici 30 minut na 180°C.', 'images/cokoladna_torta.jpg'),
('RCP-004', 'Špageti Carbonara', 'Klasična italijanska jed s špageti in kremno omako.', 'Špageti, Slanina, Jajca, Parmezan, Česen', 'Skuhaj špagete, prepraži slanino, zmešaj z jajci in parmezanom.', 'images/spageti_carbonara.jpg'),
('RCP-005', 'Zelenjavna juha', 'Lahka juha z raznoliko zelenjavo.', 'Korenje, Por, Zelenjavna juha, Peteršilj', 'Zelenjavo kuhaj v vodi, dodaj začimbe in zelišča.', 'images/zelenjavna_juha.jpg'),
('RCP-006', 'Losos na žaru', 'Okusno in zdravo meso lososa, pečeno na žaru.', 'Losos, Olivno olje, Limona, Sol, Poprova zrna', 'Lososa mariniraj in peci na žaru 10-15 minut.', 'images/losos_na_ziaru.jpg'),
('RCP-007', 'Pizza Margherita', 'Klasična pizza z paradižnikom, mozzarello in baziliko.', 'Pizza testo, Paradižnikova omaka, Mozzarella, Bazilika', 'Naredi testo, dodaj omako in sestavine, peci 15 minut.', 'images/pizza_margherita.jpg'),
('RCP-008', 'Pesto testenine', 'Testenine s svežim pesto omako.', 'Testenine, Pesto, Parmezan, Pinjole', 'Skuhaj testenine, dodaj pesto in potresi s parmezanom.', 'images/pesto_testenine.jpg'),
('RCP-009', 'Pečen piščanec', 'Sočen pečen piščanec z zelišči.', 'Piščanec, Olivno olje, Rožmarin, Česen', 'Piščanca mariniraj in peci v pečici 1 uro na 200°C.', 'images/pecen_piscanc.jpg'),
('RCP-010', 'Chili con carne', 'Mehiška jed z mletim mesom in fižolom.', 'Mleto meso, Rdeči fižol, Paradižnik, Čebula, Čili', 'Vse sestavine skuhaj skupaj in serviraj.', 'images/chili_con_carne.jpg');

-- Dodajanje testnih komentarjev
INSERT INTO comments (recipe_id, user_id, comment) VALUES
(1, 1, 'Odličen recept! Res mi je uspelo.'),
(2, 1, 'Rižota je bila super, ampak sem dodal več zelenjave.'),
(3, 2, 'Torta je bila odlična, a preveč sladka zame.');

-- Dodajanje testnih ocen
INSERT INTO ratings (recipe_id, user_id, rating) VALUES
(1, 1, 5),
(2, 1, 4),
(3, 2, 3);

-- Dodajanje testnih priljubljenih receptov
INSERT INTO bookmarks (recipe_id, user_id) VALUES
(1, 1),
(2, 2),
(3, 1);
