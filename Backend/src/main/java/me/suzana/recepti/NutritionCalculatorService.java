package me.suzana.recepti;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NutritionCalculatorService {

    /**
     * Calculates nutritional values for a recipe based on the number of servings.
     *
     * @param ingredients List of ingredients where each ingredient contains its nutritional information.
     * @param servings    Number of servings to calculate the nutritional values for.
     * @return NutritionSummary containing total and per-serving nutritional information.
     */
    public NutritionSummary calculateNutrition(List<Ingredient> ingredients, int servings) {

        double totalCalories = 0;
        double totalProteins = 0;
        double totalCarbohydrates = 0;
        double totalFats = 0;

        for (Ingredient ingredient : ingredients) {
            totalCalories += ingredient.getCalories() * ingredient.getQuantity();
            totalProteins += ingredient.getProteins() * ingredient.getQuantity();
            totalCarbohydrates += ingredient.getCarbohydrates() * ingredient.getQuantity();
            totalFats += ingredient.getFats() * ingredient.getQuantity();
        }

        double caloriesPerServing = totalCalories / servings;
        double proteinsPerServing = totalProteins / servings;
        double carbohydratesPerServing = totalCarbohydrates / servings;
        double fatsPerServing = totalFats / servings;

        return new NutritionSummary(totalCalories, totalProteins, totalCarbohydrates, totalFats,
                caloriesPerServing, proteinsPerServing, carbohydratesPerServing, fatsPerServing);
    }

    /**
     * Represents an ingredient with its nutritional information.
     */
    public static class Ingredient {
        private final String name;
        private final double quantity; // quantity in grams
        private final double calories; // calories per gram
        private final double proteins; // proteins per gram
        private final double carbohydrates; // carbohydrates per gram
        private final double fats; // fats per gram

        public Ingredient(String name, double quantity, double calories, double proteins, double carbohydrates, double fats) {
            this.name = name;
            this.quantity = quantity;
            this.calories = calories;
            this.proteins = proteins;
            this.carbohydrates = carbohydrates;
            this.fats = fats;
        }

        public String getName() {
            return name;
        }

        public double getQuantity() {
            return quantity;
        }

        public double getCalories() {
            return calories;
        }

        public double getProteins() {
            return proteins;
        }

        public double getCarbohydrates() {
            return carbohydrates;
        }

        public double getFats() {
            return fats;
        }
    }

    /**
     * Represents the summary of nutritional information.
     */
    public static class NutritionSummary {
        private final double totalCalories;
        private final double totalProteins;
        private final double totalCarbohydrates;
        private final double totalFats;
        private final double caloriesPerServing;
        private final double proteinsPerServing;
        private final double carbohydratesPerServing;
        private final double fatsPerServing;

        public NutritionSummary(double totalCalories, double totalProteins, double totalCarbohydrates, double totalFats,
                                double caloriesPerServing, double proteinsPerServing, double carbohydratesPerServing, double fatsPerServing) {
            this.totalCalories = totalCalories;
            this.totalProteins = totalProteins;
            this.totalCarbohydrates = totalCarbohydrates;
            this.totalFats = totalFats;
            this.caloriesPerServing = caloriesPerServing;
            this.proteinsPerServing = proteinsPerServing;
            this.carbohydratesPerServing = carbohydratesPerServing;
            this.fatsPerServing = fatsPerServing;
        }

        public double getTotalCalories() {
            return totalCalories;
        }

        public double getTotalProteins() {
            return totalProteins;
        }

        public double getTotalCarbohydrates() {
            return totalCarbohydrates;
        }

        public double getTotalFats() {
            return totalFats;
        }

        public double getCaloriesPerServing() {
            return caloriesPerServing;
        }

        public double getProteinsPerServing() {
            return proteinsPerServing;
        }

        public double getCarbohydratesPerServing() {
            return carbohydratesPerServing;
        }

        public double getFatsPerServing() {
            return fatsPerServing;
        }
    }
}
