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

## Namestitev in nastavitev

### Zahteve
- **Java** in **Maven** za Spring Boot backend
- **Node.js** in **npm** za frontend
- **MySQL** za bazo podatkov

### Nastavitev baze podatkov
Odprite MySQL Workbench in odprite skripto data.sql, ki se nahaja v Frontend mapi
Na začetku dodajte "Create database if not exists data" in potem to izbrišite
Zaženite SQL ukaze iz data.sql za nastavitev začetne sheme in podatkov

### Namestitev Backend-a
1. Pojdite v mapo `Backend`.
2. Nastavite MySQL poverilnice v `application.properties`.
3. Pazite da je vse v JAVA 23!
4. Zaženite aplikacijo:
   
`mvn spring-boot:run`

### Namestitev Frontend-a  
Pojdite v mapo `Frontend`.  
Namestite odvisnosti:  

`npm install`

Zaženite frontend strežnik:

`npm start`

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
- Odprite data.sql in najprej zaženite "create table if not exists data;" in potem to izbrišite in pustite vse ostalo
- `package.json` in `package-lock.json`: Datoteke za upravljanje odvisnosti frontend-a.

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

## Vizija projekta
- Namen aplikacije je ustvariti enostavno in intuitivno platformo za upravljanje receptov, ki bo uporabnikom omogočala zbiranje, shranjevanje in deljenje njihovih najljubših kuharskih receptov. S to aplikacijo želimo izboljšati uporabniško izkušnjo pri organizaciji kuharskih receptov in omogočiti lažje iskanje ter filtriranje receptov glede na različne kriterije, kot so sestavine, vrsta jedi in čas priprave. Aplikacija je namenjena tako ljubiteljskim kuharjem kot tudi profesionalcem, ki želijo imeti centralizirano mesto za shranjevanje svojih kulinaričnih idej.
- Aplikacija bo rešila problem razdrobljenosti kuharskih zapiskov in digitalizirala shranjevanje receptov, kar bo omogočilo lažje iskanje in urejanje receptov. S tem bo pripomogla k boljšemu načrtovanju obrokov in večji kreativnosti v kuhinji.

## Besednjak
- **Recept**: Popoln seznam sestavin in navodil za pripravo jedi.
- **Sestavine**: Vse snovi, ki so potrebne za pripravo določenega recepta.
- **Navodila**: Koraki, ki jih je treba slediti za uspešno pripravo recepta.
- **Kategorije receptov**: Različne vrste jedi, kot so predjedi, glavne jedi, sladice itd., ki pomagajo pri razvrščanju receptov.
- **Iskalnik**: Funkcija, ki omogoča uporabnikom hitro iskanje receptov glede na ključne besede.
- **Uporabniški profil**: Uporabnikov osebni račun, kjer lahko shranjuje in organizira svoje recepte.
- **CRUD** operacije: Operacije za ustvarjanje, branje, posodabljanje in brisanje receptov.
- **MySQL**: Baza podatkov, ki se uporablja za shranjevanje vseh informacij o receptih.
- **Frontend**: Del aplikacije, ki je namenjen uporabniški interakciji, zgrajen z uporabo Node.js.
- **Backend**: Del aplikacije, ki upravlja s strežniškimi operacijami in podatki, zgrajen z uporabo Spring Boot.
- **API**: Vmesnik za programiranje aplikacij, ki omogoča komunikacijo med frontend in backend del

## Diagram primerov uporabe

Spodaj je diagram primerov uporabe za aplikacijo za upravljanje receptov, ki prikazuje glavne interakcije med uporabniki in sistemom:



![Diagram primerov uporabe]![image](https://github.com/user-attachments/assets/8a66ebeb-1188-4248-b3b8-a53742a14459)

### Obrazložitev primerov uporabe

1. **Dodajanje recepta**: Uporabnik lahko dodaja nove recepte, vključno z imenom, opisom, sestavinami, navodili in sliko.
2. **Urejanje recepta**: Uporabnik lahko spremeni obstoječe recepte, da jih posodobi ali izboljša.
3. **Brisanje recepta**: Uporabnik lahko odstrani obstoječe recepte iz sistema, če jih ne potrebuje več.
4. **Ogled receptov**: Uporabnik lahko pregleda vse recepte, ki so na voljo v aplikaciji.
5. **Iskanje receptov**: Uporabnik lahko išče recepte po ključnih besedah, kot so ime ali določene sestavine.

### Razredni diagram

![classdiagramApp](https://github.com/user-attachments/assets/3fd0607a-f45d-4a11-9cd9-637e1d284569)

## **Backend Testiranje**

Projekt vključuje **enotne teste** za preverjanje pravilnosti ključnih funkcionalnosti backend sistema.  
Testi pokrivajo področja, kot so **ocenjevanje receptov**, **komentarji**, **PDF izvoz**, **prehranske vrednosti** in **uporabniška avtentikacija**.

---

### **Poročilo o testiranju**

Projekt vsebuje **5 testnih razredov** in skupaj **17 uspešno izvedenih testov**, ki zagotavljajo stabilnost in pravilnost logike aplikacije.

---

### **1. NutritionCalculatorServiceTest**
**Namen:** preverjanje izračuna prehranskih vrednosti na porcijo.

| Test | Vhodni podatki | Pričakovani izhod |
|:--|:--|:--|
| `testCalculateNutrition` | Sestavine: *Chicken (200 g, 2 kcal/g, 0.1 prot/g)*, *Rice (100 g, 1.3 kcal/g, 0.03 prot/g)*, 2 porciji | Skupne kalorije = **460**, beljakovine = **11.5 g/porcijo**, objekt `summary` ni `null` |

---

### **2. ReceptServiceCommentTest**
**Namen:** preverjanje dodajanja in pridobivanja komentarjev.

| Test | Vhodni podatki | Pričakovani izhod |
|:--|:--|:--|
| `testAddComment_Success` | `receptId = 1`, `userId = 1`, komentar = "Odličen recept!" | Komentar uspešno shranjen, `save()` poklican **1×** |
| `testAddComment_ReceptNotFound` | `receptId = 1` *(neobstoječ)* | `RuntimeException("Recipe not found")` |
| `testFindCommentsByReceptId_Success` | `receptId = 1` | Vrnjena **2 komentarja** ("Prvi", "Drugi") |
| `testFindCommentsByReceptId_NoComments` | `receptId = 1` | **Prazen seznam komentarjev** |

---

### **3. ReceptServiceExportTest**
**Namen:** preverjanje generiranja PDF datotek iz receptov.

| Test | Vhodni podatki | Pričakovani izhod |
|:--|:--|:--|
| `testGeneratePDF_Success` | Recept z imenom, opisom, sestavinami in navodili | PDF datoteka (byte array) vsebuje vse podatke |
| `testGeneratePDF_EmptyFields` | Recept brez opisa, sestavin in navodil | PDF vsebuje privzeta besedila: *“No description / ingredients / instructions available”* |

---

### **4. ReceptServiceTest**
**Namen:** preverjanje dodajanja ocen in izračuna prehranskih vrednosti.

| Test | Vhodni podatki | Pričakovani izhod |
|:--|:--|:--|
| `testAddOrUpdateRating_Success` | `receptId = 1`, `userId = 1`, `rating = 5` | Shrani `Rating` objekt z oceno **5** |
| `testAddOrUpdateRating_ReceptNotFound` | Neobstoječ recept | `RuntimeException("Recipe not found")` |
| `testCalculateNutritionalValuesByIngredients_Success` | Recept `"R001"`, 4 porcij, sestavine z živili | `Map` z: `recipeName`, `servings`, `totalCalories > 0`, `caloriesPerServing > 0` |
| `testCalculateNutritionalValuesByIngredients_InvalidServings` | `servings = 0` | `RuntimeException("Število porcij mora biti vsaj 1.")` |
| `testCalculateNutritionalValuesByIngredients_RecipeNotFound` | Neobstoječ recept | `RuntimeException("Recept ni najden.")` |

---

### **5. UserServiceTest**
**Namen:** preverjanje registracije in prijave uporabnikov.

| Test | Vhodni podatki | Pričakovani izhod |
|:--|:--|:--|
| `testRegisterUserSuccess` | `"testuser"`, `"test@example.com"`, `"password"` | Ustvarjen `User` objekt z istim uporabniškim imenom |
| `testLoginUserSuccess` | `"testuser"`, `"password"` | `Optional<User>` prisoten → uspešna prijava |

---

### **Povzetek testov**

| Skupina testov | Št. testov | Namen |
|:--|:--:|:--|
| `NutritionCalculatorServiceTest` | 1 | Izračun prehranskih vrednosti |
| `ReceptServiceCommentTest` | 4 | Dodajanje in iskanje komentarjev |
| `ReceptServiceExportTest` | 2 | PDF generiranje receptov |
| `ReceptServiceTest` | 5 | Ocene in izračuni |
| `UserServiceTest` | 2 | Registracija in prijava uporabnikov |
| **Skupaj** | **17** | Vsi testi uspešno izvedeni |

---

### **Zagon testov**

Za zagon vseh testov uporabite ukaz:

```bash
mvn test
```

## **Frontend Testiranje - Testni Scenarij**

- Oznaka: ReceptApp-TEST-Frontend-A5
- Aplikacija: Upravljanje receptov
- Verzija aplikacije: 1.0
- Datum scenarija: 20. 10. 2025
- Avtor: Mateja Kocbek
- Referenca: /
- Zahteve v SZPO: /

**Namen:** Preveriti pravilno delovanje uporabniškega vmesnika aplikacije za upravljanje receptov.

**Scenarij vključuje tri ključne funkcionalnosti:**
1. Dodajanje novega recepta
2. Urejanje obstoječega recepta
3. Iskanje receptov po imenu

**Predpogoji:**
- Aplikacija je uspešno nameščena in dostopna v brskalniku.
- Uporabnik ima dostop do obrazca za dodajanje in urejanje receptov.
- V bazi je shranjen vsaj en recept (“Čokoladna torta”).

### **Testni scenarij 1: Dodajanje novega recepta**

**Kratek opis:** Preveriti, ali uporabnik lahko uspešno doda nov recept prek obrazca in ali se ta pravilno prikaže v seznamu receptov.

| Korak | Opis dejanja uporabnika |	Vhodni podatki | Pričakovani rezultat |
|:--|:--|:--|:--|
| 1 |	Uporabnik odpre domačo stran aplikacije |	— | Prikaže se obrazec za dodajanje recepta |
| 2 |	Uporabnik vnese ime recepta |	“Čokoladna torta” | Polje se pravilno izpolni |
| 3 |	Uporabnik vnese opis | “Sočna torta s čokoladnim prelivom” | Besedilo se shrani v polje |
| 4 |	Uporabnik vnese sestavine | “Moka, sladkor, jajca, čokolada” |	Seznam se prikaže pravilno |
| 5 |	Uporabnik vnese navodila |	“Zmešaj sestavine in peci 30 min pri 180°C” | Polje se izpolni |
| 6 |	Klik na gumb ‘Shrani’ |	— | Sistem shrani recept in prikaže potrditveno sporočilo “Recept uspešno dodan” |
| 7 |	Preverjanje prikaza novega recepta v seznamu | — |	Novi recept “Čokoladna torta” se prikaže v seznamu |

**Pogoji uspešnega zaključka:**
- Novi recept se shrani v bazo.
- Recept se prikaže v seznamu obstoječih receptov brez napak.

**Opombe:** Če sistem ne prikaže potrditvenega sporočila, preveri povezavo med frontendom in backend API-jem /api/recepti/add.

### **Testni scenarij 2: Urejanje obstoječega recepta**

**Kratek opis:**  Preveriti pravilno delovanje funkcionalnosti urejanja že obstoječega recepta.

| Korak | Opis dejanja uporabnika |	Vhodni podatki | Pričakovani rezultat |
|:--|:--|:--|:--|
| 1 |	Uporabnik v seznamu klikne na gumb ‘Uredi’ pri receptu “Čokoladna torta” |	— | Odpre se obrazec z obstoječimi podatki |
| 2 |	Uporabnik spremeni opis | “Torta s temno čokolado in malinami” | Novi opis se prikaže v polju |
| 3 |	Klik na ‘Shrani spremembe’ | — |	Sistem shrani spremembe in prikaže obvestilo “Recept posodobljen” |
| 4 |	Preverjanje seznama receptov | —	| Posodobljen opis se pravilno prikaže |

**Pogoji uspešnega zaključka:**
- Sistem uspešno shrani spremembe.
- Posodobljeni podatki so vidni v seznamu brez osvežitve strani.

**Opombe:** Če spremembe niso vidne, preveri funkcijo updateRecipe() v frontend kodi in povezavo na /api/recepti/update.

### **Testni scenarij 3: Iskanje receptov po imenu**

**Kratek opis:**  Preveriti delovanje iskalne funkcije na seznamu receptov.

| Korak | Opis dejanja uporabnika |	Vhodni podatki | Pričakovani rezultat |
|:--|:--|:--|:--|
| 1 |	Uporabnik klikne v iskalno polje | — |	Kazalec se postavi v iskalnik |
| 2 |	Uporabnik vpiše del imena recepta |	“torta” | Sistem filtrira seznam receptov |
| 3 |	Uporabnik preveri prikazane rezultate | — | Prikazani so samo recepti, ki vsebujejo besedo “torta” |
| 4 | Uporabnik počisti iskalno polje | — | Prikazani so vsi recepti |

**Pogoji uspešnega zaključka:**
- Sistem pravilno filtrira rezultate glede na iskani niz.
- Po čiščenju polja se prikaže celoten seznam.

**Opombe:** Če filtriranje ne deluje, preveri delovanje funkcije handleSearch() in povezavo na /api/recepti/search.

**Povzetek rezultatov**
|Funkcionalnost |	Status |	Opis |
|:--|:--|:--|
| Dodajanje recepta| Uspešno | Recept se shrani in prikaže v seznamu |
| Urejanje recepta | Uspešno | Spremembe se pravilno shranijo |
| Iskanje recepta | Uspešno |	Filtriranje deluje po pričakovanjih |

**Zaključek testiranja**
- Ročno testiranje je pokazalo, da vse ključne funkcionalnosti aplikacije delujejo pravilno in stabilno.
- Uporabniški vmesnik je odziven in omogoča intuitivno uporabo.
- Med testiranjem niso bili zaznani kritični hrošči, aplikacija pa ustreza pričakovanim funkcionalnim zahtevam.

