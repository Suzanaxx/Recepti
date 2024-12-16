package me.suzana.recepti;

import com.itextpdf.text.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> calculateNutritionalValuesByIngredients(String idje, int servings) {
        // Check for valid servings
        if (servings < 1) {
            throw new RuntimeException("Število porcij mora biti vsaj 1.");
        }

        // Retrieve the recipe from the repository
        Recept recept = receptRepository.findByIdje(idje);
        if (recept == null) {
            throw new RuntimeException("Recept ni najden.");
        }

        // Extract the ingredients from the recipe
        String[] ingredientList = recept.getSestavine().split(",");

        // Initialize total nutritional values
        double totalCalories = 0.0;
        double totalProteins = 0.0;
        double totalCarbohydrates = 0.0;
        double totalFats = 0.0;
        double totalFibers = 0.0;

        for (String ingredient : ingredientList) {
            // Example ingredient format: "200 g chicken breast"
            String[] parts = ingredient.trim().split(" ", 3); // [quantity, unit, name]
            if (parts.length < 2) continue;

            try {
                double quantity = Double.parseDouble(parts[0]); // Extract quantity
                String unit = parts[1]; // Extract unit (e.g., g, ml)
                String name = parts.length > 2 ? parts[2] : "Unknown"; // Ingredient name (optional)

                // Placeholder logic for nutritional values, in reality, you would look this up
                double caloriesPerUnit = 2.0; // Example: 2 calories per gram
                double proteinsPerUnit = 0.1; // Example: 0.1g proteins per gram
                double carbsPerUnit = 0.2; // Example: 0.2g carbs per gram
                double fatsPerUnit = 0.05; // Example: 0.05g fats per gram
                double fibersPerUnit = 0.02; // Example: 0.02g fibers per gram

                // Calculate total values for the ingredient
                totalCalories += caloriesPerUnit * quantity;
                totalProteins += proteinsPerUnit * quantity;
                totalCarbohydrates += carbsPerUnit * quantity;
                totalFats += fatsPerUnit * quantity;
                totalFibers += fibersPerUnit * quantity;
            } catch (NumberFormatException e) {
                // If parsing fails, continue to the next ingredient
                continue;
            }
        }

        // Calculate per-serving values
        double caloriesPerServing = totalCalories / servings;
        double proteinsPerServing = totalProteins / servings;
        double carbsPerServing = totalCarbohydrates / servings;
        double fatsPerServing = totalFats / servings;
        double fibersPerServing = totalFibers / servings;

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("recipeName", recept.getIme());
        response.put("servings", servings);
        response.put("totalCalories", totalCalories);
        response.put("caloriesPerServing", caloriesPerServing);
        response.put("totalProteins", totalProteins);
        response.put("proteinsPerServing", proteinsPerServing);
        response.put("totalCarbohydrates", totalCarbohydrates);
        response.put("carbohydratesPerServing", carbsPerServing);
        response.put("totalFats", totalFats);
        response.put("fatsPerServing", fatsPerServing);
        response.put("totalFibers", totalFibers);
        response.put("fibersPerServing", fibersPerServing);

        return response;
    }
}
