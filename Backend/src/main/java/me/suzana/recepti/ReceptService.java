package me.suzana.recepti;

import com.itextpdf.text.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReceptService {

    @Autowired
    private ReceptRepository receptRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RatingRepository ratingRepository;

    // Konstruktor za Spring, ki podpira vse odvisnosti
    @Autowired
    public ReceptService(ReceptRepository receptRepository, RatingRepository ratingRepository, CommentRepository commentRepository) {
        this.receptRepository = receptRepository;
        this.ratingRepository = ratingRepository;
        this.commentRepository = commentRepository;
    }

    // Konstruktor za teste (brez CommentRepository)
    public ReceptService(ReceptRepository receptRepository, RatingRepository ratingRepository) {
        this.receptRepository = receptRepository;
        this.ratingRepository = ratingRepository;
    }

    // 📘 Pridobi vse recepte (vključno s hranilnimi vrednostmi)
    public List<Recept> getAllRecepti() {
        return receptRepository.findAll();
    }

    // 📘 Pridobi recept po ID-ju (vključno s hranilnimi vrednostmi)
    public Recept getReceptByIdje(String idje) {
        return receptRepository.findByIdje(idje);
    }

    // 📘 Ustvari nov recept (vključno s hranilnimi vrednostmi)
    public Recept createRecept(Recept recept) {
        // Ustvari nov recept s hranilnimi vrednostmi
        return receptRepository.save(recept);
    }

    // 📘 Posodobi obstoječi recept (vključno s hranilnimi vrednostmi)
    public Recept updateRecept(String idje, Recept receptPodatki) {
        Recept existingRecept = receptRepository.findByIdje(idje);
        if (existingRecept != null) {
            existingRecept.setIme(receptPodatki.getIme());
            existingRecept.setOpis(receptPodatki.getOpis());
            existingRecept.setSestavine(receptPodatki.getSestavine());
            existingRecept.setNavodila(receptPodatki.getNavodila());
            existingRecept.setSlika(receptPodatki.getSlika());

            // Posodobitev hranilnih vrednosti
            existingRecept.setKalorije(receptPodatki.getKalorije());
            existingRecept.setProteini(receptPodatki.getProteini());
            existingRecept.setKarbohidrati(receptPodatki.getKarbohidrati());
            existingRecept.setMascobe(receptPodatki.getMascobe());
            existingRecept.setVlaknine(receptPodatki.getVlaknine());

            return receptRepository.save(existingRecept);
        }
        return null;
    }

    // 📘 Izbriši recept
    public void deleteRecept(String idje) {
        Recept existingRecept = receptRepository.findByIdje(idje);
        if (existingRecept != null) {
            receptRepository.delete(existingRecept);
        }
    }

    // 📘 Dodaj komentar receptu
    public Comment addComment(Long recipeId, Long userId, String content) {
        if (commentRepository == null) {
            throw new RuntimeException("CommentRepository is not initialized");
        }

        Recept recept = receptRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        Comment comment = new Comment();
        comment.setRecept(recept);
        comment.setUserId(userId);
        comment.setComment(content);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    // 📘 Pridobi vse komentarje za recept
    public List<Comment> findCommentsByReceptId(Long receptId) {
        if (commentRepository == null) {
            throw new RuntimeException("CommentRepository is not initialized");
        }
        return commentRepository.findByRecept_Id(receptId);
    }

    // 📘 Posodobi ali dodaj oceno
    public Rating addOrUpdateRating(Long recipeId, Long userId, int rating) {
        Recept recept = receptRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        Rating existingRating = ratingRepository.findByRecept_IdAndUserId(recipeId, userId).orElse(null);

        if (existingRating != null) {
            existingRating.setRating(rating);
            return ratingRepository.save(existingRating);
        }

        Rating newRating = new Rating();
        newRating.setRecept(recept);
        newRating.setUserId(userId);
        newRating.setRating(rating);
        newRating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(newRating);
    }

    // 📘 Generiranje PDF-ja (vključno s hranilnimi vrednostmi)
    public byte[] generatePDF(Recept recept) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Add Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("Recipe: " + recept.getIme(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ")); // Blank line

            // Add Description
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            document.add(new Paragraph("Description:", sectionFont));
            document.add(new Paragraph(recept.getOpis() != null ? recept.getOpis() : "No description available"));

            document.add(new Paragraph(" ")); // Blank line

            // Add Ingredients
            document.add(new Paragraph("Ingredients:", sectionFont));
            document.add(new Paragraph(recept.getSestavine() != null ? recept.getSestavine() : "No ingredients available"));

            document.add(new Paragraph(" ")); // Blank line

            // Add Instructions
            document.add(new Paragraph("Instructions:", sectionFont));
            document.add(new Paragraph(recept.getNavodila() != null ? recept.getNavodila() : "No instructions available"));

            document.add(new Paragraph(" ")); // Blank line

            // Dodaj hranilne vrednosti
            document.add(new Paragraph("Nutritional Information (per serving):", sectionFont));
            document.add(new Paragraph("Calories: " + recept.getKalorije() + " kcal"));
            document.add(new Paragraph("Proteins: " + recept.getProteini() + " g"));
            document.add(new Paragraph("Carbohydrates: " + recept.getKarbohidrati() + " g"));
            document.add(new Paragraph("Fats: " + recept.getMascobe() + " g"));
            document.add(new Paragraph("Fibers: " + recept.getVlaknine() + " g"));

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
