# Poročilo o testiranju

## Opis testov
1. **Dodajanje nove ocene (uspešen primer)**:
    - Preverja, ali lahko uporabnik uspešno doda oceno za obstoječ recept.
    - Test uporablja simulacijo (`Mockito`) za ponaredek podatkov o receptu, da preveri, ali se ocena pravilno shrani v podatkovno bazo.
    - Izvedba preveri tudi, ali metoda `save` na repozitoriju ocene (`RatingRepository`) deluje kot pričakovano.
2. **Napaka pri dodajanju ocene (recept ne obstaja)**:
    - Preverja, ali metoda vrže izjemo `RuntimeException`, ko recept ni najden v repozitoriju.
    - Ta test zajema negativni scenarij, ki se zgodi, če uporabnik poskuša oceniti neobstoječ recept.

## Člani skupine
- **Suzana Lesjak Glavač**: Odgovorna za implementacijo in testiranje funkcionalnosti izvoza receptov v PDF.
- **Tilen Grobelšek**: Odgovoren za implementacijo in testiranje funkcionalnosti komentarjev.
- **Julio Mati**: Odgovoren za implementacijo in testiranje funkcionalnosti ocenjevanja.

## Uspešnost testov
- Vsi testi so bili uspešno izvedeni.
    - Test *Dodajanje nove ocene* se je uspešno zaključil z zelenim statusom (`✔`).
    - Test *Napaka pri dodajanju ocene* je pravilno vrnil izjemo in se uspešno zaključil.
- Robni primeri so ustrezno zajeti:
    - Scenariji za obstoječe in neobstoječe recepte.
    - Preverjanje izjem in pravilnih rezultatov metod.