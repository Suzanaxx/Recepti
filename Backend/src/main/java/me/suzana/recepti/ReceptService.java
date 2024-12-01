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

    public ReceptService(ReceptRepository receptRepository, RatingRepository ratingRepository) {
        this.receptRepository = receptRepository;
        this.ratingRepository = ratingRepository;
    }
    // Pridobi vse recepte
    public List<Recept> getAllRecepti() {
        return receptRepository.findAll();
    }

    // Pridobi recept po ID-ju
    public Recept getReceptByIdje(String idje) {
        return receptRepository.findByIdje(idje);
    }

    // Ustvari nov recept
    public Recept createRecept(Recept recept) {
        return receptRepository.save(recept);
    }

    // Posodobi obstoječi recept
    public Recept updateRecept(String idje, Recept recept) {
        Recept existingRecept = receptRepository.findByIdje(idje);
        if (existingRecept != null) {
            existingRecept.setIme(recept.getIme());
            existingRecept.setOpis(recept.getOpis());
            existingRecept.setSestavine(recept.getSestavine());
            existingRecept.setNavodila(recept.getNavodila());
            existingRecept.setSlika(recept.getSlika());
            return receptRepository.save(existingRecept);
        }
        return null;
    }

    // Izbriši recept
    public void deleteRecept(String idje) {
        Recept existingRecept = receptRepository.findByIdje(idje);
        if (existingRecept != null) {
            receptRepository.delete(existingRecept);
        }
    }

    // Pridobi vse komentarje za recept
    // Fetch comments for a specific recipe
    public List<Comment> getComments(Long recipeId) {
        return commentRepository.findByRecept_Id(recipeId);
    }

    // Add a comment to a specific recipe
    public Comment addComment(Long recipeId, Long userId, String content) {
        Recept recept = receptRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        Comment comment = new Comment();
        comment.setRecept(recept);
        comment.setUserId(userId);
        comment.setComment(content);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }


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

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}

