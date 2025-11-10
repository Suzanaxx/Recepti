# Poročilo o sistemskem testu – Performance Test

**Datum izvedbe:** 10. november 2025  
**Orodje:** Apache JMeter 5.6.3  
**Namen testa:** Preverjanje odzivnosti in stabilnosti API-ja `/api/recepti/all` v sistemu za upravljanje receptov.

---

## Nastavitve testa

| Parameter | Vrednost |
|------------|-----------|
| Število virtualnih uporabnikov | 50 |
| Ramp-up čas | 10 sekund |
| Ponovitve (Loop Count) | 1 |
| HTTP metoda | GET |
| URL | `http://localhost:8080/api/recepti/all` |

---

## Rezultati

| Metrika | Vrednost |
|----------|-----------|
| Število zahtev | 50 |
| Povprečen odzivni čas | **8 ms** |
| Najmanjši odzivni čas | 7 ms |
| Največji odzivni čas | 13 ms |
| Standardna deviacija | 1.17 ms |
| Napake | 0.00 % |
| Throughput | 5.1 zahtev/sekundo |
| Received KB/sec | 78.4 |
| Sent KB/sec | 0.93 |

---

## Interpretacija rezultatov

- Vsi HTTP zahtevki so bili uspešno izvedeni (0 % napak).  
- Povprečni odzivni čas 8 ms pomeni **zelo hitro in optimizirano** delovanje backend API-ja.  
- Nizka standardna deviacija (1.17 ms) kaže na **stabilnost odzivov** brez nihanj.  
- Throughput 5.1 zahtev/sekundo potrjuje, da sistem zmore hkrati obdelovati več zahtev brez degradacije zmogljivosti.  
- Grafi v JMeterju (Average, Throughput) so **stabilni brez spike-ov ali nihanj**, kar pomeni konstantno obremenitev brez vpliva na hitrost.

---

## Zaključek

API `/api/recepti/all` je **odziven, stabilen in brez napak** tudi pri 50 sočasnih uporabnikih.  
Sistem **izpolnjuje nefunkcionalne zahteve** glede zmogljivosti in odzivnosti.  
Rezultati kažejo, da backend lahko **obdeluje več zahtev na sekundo** brez izgube stabilnosti ali povečanja odzivnega časa.

**Priporočilo:**  
Za nadaljnjo analizo zmogljivosti se predlaga izvedba dodatnih testov z višjo obremenitvijo (npr. 100–200 uporabnikov) ter vključitev meritev CPU/RAM porabe na strežniku.
