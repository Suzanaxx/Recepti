# Aplikacija za upravljanje receptov ( Suzana Lesjak Glavač, Tilen Grobelšek, Julio Mati)

## Pregled
To je spletna aplikacija za upravljanje receptov, kjer lahko uporabniki dodajajo, urejajo, brišejo in iščejo recepte. Projekt uporablja **Spring Boot** za backend, **Node.js** za frontend ter **MySQL** kot bazo podatkov za shranjevanje informacij o receptih. Aplikacija omogoča enostavno komunikacijo med frontend in backend delom.

## Funkcionalnosti
- **Dodajanje receptov**: Dodajte nove recepte z informacijami, kot so ime, opis, sestavine, navodila in slika.
- **Ogled receptov**: Prikaz vseh razpoložljivih receptov, z možnostjo iskanja po ključnih besedah.
- **Urejanje receptov**: Urejanje obstoječih informacij o receptu.
- **Brisanje receptov**: Odstranite recepte iz baze podatkov.
- **Funkcionalnost iskanja**: Iskanje receptov po imenu.
- **Odziven vmesnik**: Aplikacija uporablja Bootstrap za prijazno obliko na mobilnih napravah.

## Struktura projekta
Projekt je organiziran v naslednje direktorije:

### Backend
Vsebuje kodo Spring Boot backend-a.

- `src/main/java/me/suzana/recepti`: 
  - `Recept.java`: Entiteta, ki predstavlja recept.
  - `ReceptController.java`: Kontroler za obravnavo HTTP zahtev, povezanih z recepti.
  - `ReceptRepository.java`: Vmesnik za interakcijo z MySQL bazo podatkov.
  - `ReceptService.java`: Service sloj za poslovno logiko.
  - `StranZaRecepteApplication.java`: Glavni razred aplikacije Spring Boot.
  - `WebConfig.java`: Konfiguracijska datoteka za CORS ali druge spletne nastavitve.

### Frontend
Vsebuje datoteke frontend-a, ki jih upravlja Node.js.

- `index.html`: Glavna HTML datoteka z obrazcem za dodajanje in prikaz receptov.
- `FetchingData.js`: JavaScript datoteka za obravnavo API klicev in dinamičen prikaz receptov v uporabniškem vmesniku.
- `data.sql`: Primer podatkov za inicializacijo MySQL baze podatkov.
- `package.json` in `package-lock.json`: Datoteke za upravljanje odvisnosti frontend-a.

## Namestitev in nastavitev

### Zahteve
- **Java** in **Maven** za Spring Boot backend
- **Node.js** in **npm** za frontend
- **MySQL** za bazo podatkov

### Namestitev Backend-a
1. Pojdite v mapo `Backend`.
2. Nastavite MySQL poverilnice v `application.properties`.
3. Zaženite aplikacijo:
   
`mvn spring-boot:run`

### Namestitev Frontend-a  
Pojdite v mapo `Frontend`.  
Namestite odvisnosti:  

`npm install`

Zaženite frontend strežnik:

`npm start`

### Nastavitev baze podatkov
Ustvarite MySQL bazo za aplikacijo.
Zaženite SQL ukaze iz data.sql za nastavitev začetne sheme in podatkov

## API Končne točke
Tukaj so glavne API končne točke, ki jih uporablja aplikacija:

- **GET /api/recepti**: Pridobi vse recepte.
- **POST /api/recepti**: Dodaj nov recept.
- **PUT /api/recepti/{id}**: Posodobi obstoječi recept.
- **DELETE /api/recepti/{id}**: Izbriši recept po ID-ju.

## Prihodnje nadgradnje

- **Kategorije receptov**: Možnost kategorizacije receptov (npr. predjed, glavna jed).
- **Avtentikacija uporabnikov**: Dodaj prijavo in registracijo uporabnikov za osebno upravljanje receptov.
- **Ocene in komentarji**: Omogoči uporabnikom ocenjevanje in komentiranje receptov.
- **Obvestila**: Implementiraj sistem obveščanja uporabnikov o novih receptih ali opomnikih.

## Vizija projekta
Namen aplikacije je ustvariti enostavno in intuitivno platformo za upravljanje receptov, ki bo uporabnikom omogočala zbiranje, shranjevanje in deljenje njihovih najljubših kuharskih receptov. S to aplikacijo želimo izboljšati uporabniško izkušnjo pri organizaciji kuharskih receptov in omogočiti lažje iskanje ter filtriranje receptov glede na različne kriterije, kot so sestavine, vrsta jedi in čas priprave. Aplikacija je namenjena tako ljubiteljskim kuharjem kot tudi profesionalcem, ki želijo imeti centralizirano mesto za shranjevanje svojih kulinaričnih idej.

Aplikacija bo rešila problem razdrobljenosti kuharskih zapiskov in digitalizirala shranjevanje receptov, kar bo omogočilo lažje iskanje in urejanje receptov. S tem bo pripomogla k boljšemu načrtovanju obrokov in večji kreativnosti v kuhinji.

Besednjak
Recept: Popoln seznam sestavin in navodil za pripravo jedi.
Sestavine: Vse snovi, ki so potrebne za pripravo določenega recepta.
Navodila: Koraki, ki jih je treba slediti za uspešno pripravo recepta.
Kategorije receptov: Različne vrste jedi, kot so predjedi, glavne jedi, sladice itd., ki pomagajo pri razvrščanju receptov.
Iskalnik: Funkcija, ki omogoča uporabnikom hitro iskanje receptov glede na ključne besede.
Uporabniški profil: Uporabnikov osebni račun, kjer lahko shranjuje in organizira svoje recepte.
CRUD operacije: Operacije za ustvarjanje, branje, posodabljanje in brisanje receptov.
MySQL: Baza podatkov, ki se uporablja za shranjevanje vseh informacij o receptih.
Frontend: Del aplikacije, ki je namenjen uporabniški interakciji, zgrajen z uporabo Node.js.
Backend: Del aplikacije, ki upravlja s strežniškimi operacijami in podatki, zgrajen z uporabo Spring Boot.
API: Vmesnik za programiranje aplikacij, ki omogoča komunikacijo med frontend in backend delom aplikacije.

## Diagram primerov uporabe

Spodaj je diagram primerov uporabe za aplikacijo za upravljanje receptov, ki prikazuje glavne interakcije med uporabniki in sistemom:

![Diagram primerov uporabe](./pot-do-diagrama.png)

### Obrazložitev primerov uporabe

1. **Dodajanje recepta**: Uporabnik lahko dodaja nove recepte, vključno z imenom, opisom, sestavinami, navodili in sliko.
2. **Urejanje recepta**: Uporabnik lahko spremeni obstoječe recepte, da jih posodobi ali izboljša.
3. **Brisanje recepta**: Uporabnik lahko odstrani obstoječe recepte iz sistema, če jih ne potrebuje več.
4. **Ogled receptov**: Uporabnik lahko pregleda vse recepte, ki so na voljo v aplikaciji.
5. **Iskanje receptov**: Uporabnik lahko išče recepte po ključnih besedah, kot so ime ali določene sestavine.
