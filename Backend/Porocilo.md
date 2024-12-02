# Poročilo o testiranju

## Opis testov
1. **Dodajanje nove ocene (uspešen primer)**:
    - Preverja, ali lahko uporabnik uspešno doda oceno za obstoječ recept.
    - Test uporablja simulacijo (`Mockito`) za ponaredek podatkov o receptu, da preveri, ali se ocena pravilno shrani v podatkovno bazo.
    - Izvedba preveri tudi, ali metoda `save` na repozitoriju ocene (`RatingRepository`) deluje kot pričakovano.
2. **Napaka pri dodajanju ocene (recept ne obstaja)**:
    - Preverja, ali metoda vrže izjemo `RuntimeException`, ko recept ni najden v repozitoriju.
    - Ta test zajema negativni scenarij, ki se zgodi, če uporabnik poskuša oceniti neobstoječ recept.
3. **Izvoz recepta v PDF (uspešen primer)**:
    - Preverja, ali metoda za generiranje PDF-ja uspešno ustvari PDF s pričakovanimi podatki recepta.
    - Preverjanje vključuje, ali PDF vsebuje ime, opis, sestavine in navodila recepta.
    - Uporabljena knjižnica `PDFBox` za analizo PDF-ja in preverjanje vsebine.
4. **Izvoz recepta v PDF (manjkajoča polja)**:
    - Preverja, ali metoda za generiranje PDF-ja ustvari PDF z privzetimi besedili za prazna polja recepta (npr. `No description available`).
    - Test zajema robne primere za prazna polja, kjer so sestavine, navodila ali opis manjkajoči.
5. **Dodajanje komentarja (uspešen primer)**:
    - Preverja, ali lahko uporabnik uspešno doda komentar na obstoječ recept.
    - Test uporablja simulacijo (`Mockito`) za ponaredek podatkov o receptu in preverja, ali se komentar pravilno shrani v podatkovno bazo.
    - Izvedba preveri tudi, ali metoda `save` na repozitoriju komentarjev (`CommentRepository`) deluje kot pričakovano.
6. **Napaka pri dodajanju komentarja (recept ne obstaja)**:
    - Preverja, ali metoda vrže izjemo `RuntimeException`, ko recept ni najden v repozitoriju pri dodajanju komentarja.
    - Ta test zajema negativni scenarij, ki se zgodi, če uporabnik poskuša dodati komentar na neobstoječ recept.
7. **Pridobivanje komentarjev za recept (uspešen primer)**:
    - Preverja, ali se lahko uspešno pridobijo komentarji za obstoječ recept.
    - Test uporablja simulacijo za preverjanje, ali metoda `findByRecept_Id` v `CommentRepository` vrne pravilne komentarje.
8. **Pridobivanje komentarjev za recept (brez komentarjev)**:
    - Preverja, ali metoda `findByRecept_Id` vrne prazen seznam, če za recept ni komentarjev.

## Člani skupine
- **Suzana Lesjak Glavač**: Odgovorna za implementacijo in testiranje funkcionalnosti izvoza receptov v PDF.
- **Tilen Grobelšek**: Odgovoren za implementacijo in testiranje funkcionalnosti komentarjev.
- **Julio Mati**: Odgovoren za implementacijo in testiranje funkcionalnosti ocenjevanja.

## Uspešnost testov
- Vsi testi so bili uspešno izvedeni.
    - Test *Dodajanje nove ocene* se je uspešno zaključil z zelenim statusom (`✔`).
    - Test *Napaka pri dodajanju ocene* je pravilno vrnil izjemo in se uspešno zaključil.
    - Test *Izvoz recepta v PDF* je uspešno ustvaril PDF s pravilnimi podatki recepta.
    - Test *Izvoz recepta v PDF (manjkajoča polja)* je uspešno ustvaril PDF z privzetimi vrednostmi za manjkajoča polja.
    - Test *Dodajanje komentarja* je uspešno dodalo komentar na obstoječ recept.
    - Test *Napaka pri dodajanju komentarja* je pravilno vrnil izjemo, ko recept ni obstajal.
    - Test *Pridobivanje komentarjev za recept* je uspešno pridobil komentarje za recept.
    - Test *Pridobivanje komentarjev za recept (brez komentarjev)* je pravilno vrnil prazen seznam, ko ni bilo komentarjev za recept.
- Robni primeri so ustrezno zajeti:
    - Scenariji za obstoječe in neobstoječe recepte.
    - Preverjanje izjem in pravilnih rezultatov metod.