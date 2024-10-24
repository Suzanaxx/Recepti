-- Creating the database (if it doesn't exist)
CREATE DATABASE IF NOT EXISTS data;

-- Selecting the database to use
USE data;

-- Dropping the table if it exists
DROP TABLE IF EXISTS recepti;

-- Creating the recepti table
CREATE TABLE recepti (
    id SERIAL PRIMARY KEY,
    idje VARCHAR(20) NOT NULL UNIQUE,
    ime VARCHAR(255) NOT NULL,
    opis TEXT,
    sestavine TEXT,
    navodila TEXT,
    slika VARCHAR(255)
);


-- Adding initial recipes to the recepti table
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
