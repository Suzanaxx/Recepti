package me.suzana.recepti;

import org.springframework.beans.factory.annotation.Autowired;
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

    // Pridobi vse recepte
    @GetMapping
    public List<Recept> getAllRecepti() {
        return receptService.getAllRecepti();
    }


    // Pridobi recept po ID-ju
    @GetMapping("/{idje}")
    public Recept getReceptByIdje(@PathVariable String idje) {
        return receptService.getReceptByIdje(idje);
    }

    // Ustvari nov recept
    @PostMapping
    public Recept createRecept(@RequestBody Recept recept) {
        return receptService.createRecept(recept);
    }

    // Posodobi obstoječi recept
    @PutMapping("/{idje}")
    public Recept updateRecept(@PathVariable String idje, @RequestBody Recept recept) {
        return receptService.updateRecept(idje, recept);
    }

    // Izbriši recept
    @DeleteMapping("/{idje}")
    public void deleteRecept(@PathVariable String idje) {
        receptService.deleteRecept(idje);
    }

    // Pridobi komentarje za recept
    // Fetch comments for a recipe
    @GetMapping("/{recipeId}/comments")
    public List<Comment> getCommentsForRecipe(@PathVariable Long recipeId) {
        return receptService.getComments(recipeId);
    }


    @PostMapping("/{recipeId}/comments")
    public Comment addCommentToRecipe(
            @PathVariable Long recipeId,
            @RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        String comment = payload.get("comment");

        if (userId == null || comment == null) {
            throw new RuntimeException("Missing required fields: userId or comment");
        }

        return receptService.addComment(recipeId, Long.parseLong(userId), comment);
    }



    @PostMapping("/{recipeId}/ratings")
    public ResponseEntity<String> submitRating(@PathVariable Long recipeId, @RequestBody Map<String, Object> payload) {
        System.out.println("Received rating for recipeId: " + recipeId + ", Payload: " + payload);

        Long userId = Long.valueOf(payload.get("userId").toString());
        int rating = Integer.parseInt(payload.get("rating").toString());

        receptService.addOrUpdateRating(recipeId, userId, rating);
        return ResponseEntity.ok("Rating submitted successfully");
    }
    @PostMapping("/{idje}/export-pdf")
    public ResponseEntity<byte[]> exportRecipeToPDF(@PathVariable String idje) {
        Recept recept = receptService.getReceptByIdje(idje);
        if (recept == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] pdfBytes = receptService.generatePDF(recept);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + recept.getIme() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }







}
