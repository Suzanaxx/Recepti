package me.suzana.recepti;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NutritionCalculatorServiceTest {

    @Test
    void testCalculateNutrition() {
        NutritionCalculatorService service = new NutritionCalculatorService();

        NutritionCalculatorService.Ingredient ingredient1 =
                new NutritionCalculatorService.Ingredient("Chicken", 200, 2, 0.1, 0.2, 0.05);
        NutritionCalculatorService.Ingredient ingredient2 =
                new NutritionCalculatorService.Ingredient("Rice", 100, 1.3, 0.03, 0.28, 0.01);

        NutritionCalculatorService.NutritionSummary summary =
                service.calculateNutrition(List.of(ingredient1, ingredient2), 2);

        assertNotNull(summary);
        assertEquals(200*2 + 100*1.3, summary.getTotalCalories(), 0.001);
        assertEquals((200*0.1 + 100*0.03)/2, summary.getProteinsPerServing(), 0.001);
    }
}
