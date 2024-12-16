package me.suzana.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/recepti")
public class ReceptController {

    @Autowired
    private ReceptService receptService;

    // 📘 Pridobi vse recepte (vključno s hranilnimi vrednostmi)
    @GetMapping
    public List<Recept> getAllRecepti() {
        return receptService.getAllRecepti();
    }

    // 📘 Pridobi recept po ID-ju (vključno s hranilnimi vrednostmi)
    @GetMapping("/{idje}")
    public Recept getReceptByIdje(@PathVariable String idje) {
        return receptService.getReceptByIdje(idje);
    }

    // 📘 Ustvari nov recept (vključno s hranilnimi vrednostmi)
    @PostMapping
    public ResponseEntity<Recept> createRecept(@RequestBody Recept recept) {
        try {
            Recept newRecept = receptService.createRecept(recept);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRecept);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 📘 Posodobi obstoječi recept (vključno s hranilnimi vrednostmi)
    @PutMapping("/{idje}")
    public ResponseEntity<Recept> updateRecept(@PathVariable String idje, @RequestBody Recept receptPodatki) {
        try {
            Recept updatedRecept = receptService.updateRecept(idje, receptPodatki);
            if (updatedRecept == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(updatedRecept);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 📘 Izbriši recept
    @DeleteMapping("/{idje}")
    public ResponseEntity<Void> deleteRecept(@PathVariable String idje) {
        try {
            receptService.deleteRecept(idje);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 📘 Pridobi komentarje za določen recept
    @GetMapping("/{recipeId}/comments")
    public ResponseEntity<List<Comment>> getCommentsForRecipe(@PathVariable Long recipeId) {
        List<Comment> comments = receptService.findCommentsByReceptId(recipeId);
        if (comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(comments);
    }

    // 📘 Dodaj komentar k receptu
    @PostMapping("/{recipeId}/comments")
    public ResponseEntity<Comment> addCommentToRecipe(
            @PathVariable Long recipeId,
            @RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        String comment = payload.get("comment");

        if (userId == null || comment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            Comment newComment = receptService.addComment(recipeId, Long.parseLong(userId), comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 📘 Oddaj oceno za recept
    @PostMapping("/{recipeId}/ratings")
    public ResponseEntity<String> submitRating(@PathVariable Long recipeId, @RequestBody Map<String, Object> payload) {
        System.out.println("Received rating for recipeId: " + recipeId + ", Payload: " + payload);

        Long userId = Long.valueOf(payload.get("userId").toString());
        int rating = Integer.parseInt(payload.get("rating").toString());

        try {
            receptService.addOrUpdateRating(recipeId, userId, rating);
            return ResponseEntity.ok("Rating submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error submitting rating");
        }
    }

    // 📘 Izvoz recepta v PDF
    @PostMapping("/{idje}/export-pdf")
    public ResponseEntity<byte[]> exportRecipeToPDF(@PathVariable String idje) {
        Recept recept = receptService.getReceptByIdje(idje);
        if (recept == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] pdfBytes = receptService.generatePDF(recept);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recept.getIme() + ".pdf\"")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
