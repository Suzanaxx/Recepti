package me.suzana.recepti;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
>>>>>>> parent of 3450311 (izvoz)

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
    public List<Comment> getComments(Long recipeId) {
        return commentRepository.findByRecept_Id(recipeId);
    }

    // Dodaj komentar k receptu
    public Comment addComment(Long recipeId, Long userId, String content) {
        Recept recept = receptRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recept not found"));

        Comment comment = new Comment();
        comment.setRecept(recept);
        comment.setUserId(userId);
        comment.setComment(content);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    // Pridobi vse ocene za recept
    public List<Rating> getRatings(Long recipeId) {
        return ratingRepository.findByRecept_Id(recipeId);
    }

    // Dodaj oceno k receptu
    public Rating addRating(Long recipeId, Long userId, int rating) {
        Recept recept = receptRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recept not found"));

        Rating newRating = new Rating();
        newRating.setRecept(recept);
        newRating.setUserId(userId);
        newRating.setRating(rating);
        newRating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(newRating);
    }

    public byte[] generatePDF(Recept recept) {
        // Generate PDF
        return new byte[0];
    }
}
