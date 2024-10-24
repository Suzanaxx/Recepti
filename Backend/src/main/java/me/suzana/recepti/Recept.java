package me.suzana.recepti;

import jakarta.persistence.*;

@Entity
@Table(name = "recepti")
public class Recept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String idje;

    @Column(nullable = false)
    private String ime;

    @Column
    private String opis;

    @Column
    private String sestavine;

    @Column
    private String navodila;

    @Column
    private String slika;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdje() {
        return idje;
    }

    public void setIdje(String idje) {
        this.idje = idje;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSestavine() {
        return sestavine;
    }

    public void setSestavine(String sestavine) {
        this.sestavine = sestavine;
    }

    public String getNavodila() {
        return navodila;
    }

    public void setNavodila(String navodila) {
        this.navodila = navodila;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }
}
