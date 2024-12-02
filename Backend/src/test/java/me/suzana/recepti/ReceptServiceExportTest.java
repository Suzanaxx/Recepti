package me.suzana.recepti;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceptServiceExportTest {

    private ReceptService receptService;
    private ReceptRepository receptRepository;

    @BeforeEach
    void setUp() {
        // Mockiranje repozitorija
        receptRepository = mock(ReceptRepository.class);
        receptService = new ReceptService(receptRepository, null); // Drugi parameter ni potreben za testiranje PDF funkcionalnosti
    }

    @Test
    void testGeneratePDF_Success() throws IOException {
        // Priprava podatkov
        Recept recept = new Recept();
        recept.setIme("Testni Recept");
        recept.setOpis("To je testni opis recepta.");
        recept.setSestavine("Sestavine: Jajca, Moka, Mleko.");
        recept.setNavodila("Navodila: Zmešaj in speci.");

        // Generiranje PDF-ja
        byte[] pdfBytes = receptService.generatePDF(recept);

        // Preverjanje, ali PDF ni prazen
        assertNotNull(pdfBytes, "PDF byte array naj ne bo null.");
        assertTrue(pdfBytes.length > 0, "PDF vsebina naj ne bo prazna.");

        // Uporaba PDFBox za preverjanje vsebine PDF-ja
        try (PDDocument pdfDocument = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            String pdfText = new PDFTextStripper().getText(pdfDocument);

            // Preverjanje vsebine PDF-ja
            assertTrue(pdfText.contains("Testni Recept"), "PDF mora vsebovati ime recepta.");
            assertTrue(pdfText.contains("To je testni opis recepta."), "PDF mora vsebovati opis recepta.");
            assertTrue(pdfText.contains("Sestavine: Jajca, Moka, Mleko."), "PDF mora vsebovati sestavine.");
            assertTrue(pdfText.contains("Navodila: Zmešaj in speci."), "PDF mora vsebovati navodila.");
        }
    }


    @Test
    void testGeneratePDF_EmptyFields() throws IOException {
        // Priprava podatkov za recept brez vsebine
        Recept recept = new Recept();
        recept.setIme("Prazen Recept");

        // Generiranje PDF-ja
        byte[] pdfBytes = receptService.generatePDF(recept);

        // Uporaba PDFBox za preverjanje vsebine PDF-ja
        try (PDDocument pdfDocument = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            String pdfText = new PDFTextStripper().getText(pdfDocument);

            // Preverjanje vsebine PDF-ja
            assertTrue(pdfText.contains("Prazen Recept"), "PDF mora vsebovati ime recepta.");
            assertTrue(pdfText.contains("No description available"), "PDF mora vsebovati privzeto besedilo za opis.");
            assertTrue(pdfText.contains("No ingredients available"), "PDF mora vsebovati privzeto besedilo za sestavine.");
            assertTrue(pdfText.contains("No instructions available"), "PDF mora vsebovati privzeto besedilo za navodila.");
        }
    }
}