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

    @GetMapping
    public List<Recept> getAllRecepti() {
        return receptService.getAllRecepti();
    }

    @GetMapping("/{idje}")
    public Recept getReceptByIdje(@PathVariable String idje) {
        return receptService.getReceptByIdje(idje);
    }

    // Create a new recipe
    @PostMapping
    public Recept createRecept(@RequestBody Recept recept) {
        return receptService.createRecept(recept);
    }

    // Update an existing recipe
    @PutMapping("/{idje}")
    public Recept updateRecept(@PathVariable String idje, @RequestBody Recept recept) {
        return receptService.updateRecept(idje, recept);
    }

    // Delete a recipe
    @DeleteMapping("/{idje}")
    public void deleteRecept(@PathVariable String idje) {
        receptService.deleteRecept(idje);
    }
}
