
package me.suzana.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReceptService {

    @Autowired
    private ReceptRepository receptRepository;

    @Autowired
    private CommentRepository commentRepository;

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
        return null; // Lahko tudi vržete izjemo, če recept ne obstaja
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
}