-- Uporaba baze
USE data;

-- Tabela za uporabnike
CREATE TABLE IF NOT EXISTS users (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- Primarni ključ (INT UNSIGNED za skladnost)
    username VARCHAR(50) NOT NULL UNIQUE,            -- Unikatno uporabniško ime
    email VARCHAR(100) NOT NULL UNIQUE,              -- Unikaten email
    password VARCHAR(255) NOT NULL                  -- Geslo (hashirano)
);

-- Tabela za recepte
CREATE TABLE IF NOT EXISTS recepti (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- Primarni ključ (INT UNSIGNED za skladnost)
    idje VARCHAR(20) NOT NULL UNIQUE,                -- Unikatna oznaka recepta
    ime VARCHAR(255) NOT NULL,                       -- Ime recepta
    opis TEXT,                                       -- Kratek opis recepta
    sestavine TEXT,                                  -- Sestavine recepta
    navodila TEXT,                                   -- Navodila za pripravo recepta
    slika VARCHAR(255)                               -- URL slike recepta
);

-- Tabela za komentarje
CREATE TABLE IF NOT EXISTS comments (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- Primarni ključ
    recipe_id INT UNSIGNED NOT NULL,                  -- Tuji ključ na tabelo recepti
    user_id INT UNSIGNED NOT NULL,                    -- Tuji ključ na tabelo users
    comment TEXT NOT NULL,                            -- Besedilo komentarja
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- Datum komentarja
    FOREIGN KEY (recipe_id) REFERENCES recepti(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela za ocene
CREATE TABLE IF NOT EXISTS ratings (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- Primarni ključ
    recipe_id INT UNSIGNED NOT NULL,                  -- Tuji ključ na tabelo recepti
    user_id INT UNSIGNED NOT NULL,                    -- Tuji ključ na tabelo users
    rating TINYINT UNSIGNED CHECK (rating BETWEEN 1 AND 5), -- Ocena (1-5)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- Datum ocene
    FOREIGN KEY (recipe_id) REFERENCES recepti(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela za priljubljene recepte
CREATE TABLE IF NOT EXISTS bookmarks (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- Primarni ključ
    recipe_id INT UNSIGNED NOT NULL,                  -- Tuji ključ na tabelo recepti
    user_id INT UNSIGNED NOT NULL,                    -- Tuji ključ na tabelo users
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- Datum dodajanja
    FOREIGN KEY (recipe_id) REFERENCES recepti(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

