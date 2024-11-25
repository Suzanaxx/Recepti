package me.suzana.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{recipeId}/comments")
    public List<Comment> getComments(@PathVariable Long recipeId) {
        return receptService.getComments(recipeId);
    }

    // Dodaj komentar k receptu
    @PostMapping("/{recipeId}/comments")
    public Comment addComment(@PathVariable Long recipeId, @RequestParam Long userId, @RequestBody String content) {
        return receptService.addComment(recipeId, userId, content);
    }
}