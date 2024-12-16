package me.suzana.recepti;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceptServiceTest {

    private ReceptService receptService;
    private RatingRepository ratingRepository;
    private ReceptRepository receptRepository;

    @BeforeEach
    void setUp() {
        // Mock the dependencies
        ratingRepository = mock(RatingRepository.class);
        receptRepository = mock(ReceptRepository.class);

        // Inject the mocks into the service
        receptService = new ReceptService(receptRepository, ratingRepository);
    }

    @Test
    void testAddOrUpdateRating_Success() {
        // Prepare test data
        Recept recept = new Recept();
        recept.setId(1L);

        Rating rating = new Rating();
        rating.setUserId(1L);
        rating.setRating(5);

        when(receptRepository.findById(1L)).thenReturn(Optional.of(recept));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        // Call the method under test
        Rating result = receptService.addOrUpdateRating(1L, 1L, 5);

        // Verify the results
        assertEquals(5, result.getRating());
        assertEquals(1L, result.getUserId());
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    void testAddOrUpdateRating_ReceptNotFound() {
        // Prepare mock behavior
        when(receptRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the method and assert exception
        Exception exception = assertThrows(RuntimeException.class, () ->
                receptService.addOrUpdateRating(1L, 1L, 5)
        );

        assertEquals("Recipe not found", exception.getMessage());
    }

    @Test
    void testCalculateNutritionalValuesByIngredients_Success() {
        // Mock recipe data
        Recept recept = new Recept();
        recept.setIdje("R001");
        recept.setIme("Test Recipe");
        recept.setSestavine("200 g chicken breast, 100 g rice, 50 g broccoli");

        // Mock repository response
        when(receptRepository.findByIdje("R001")).thenReturn(recept);

        // Call the method under test
        Map<String, Object> result = receptService.calculateNutritionalValuesByIngredients("R001", 4);

        // Verify the results
        assertNotNull(result);
        assertEquals("Test Recipe", result.get("recipeName"));
        assertEquals(4, result.get("servings"));
        assertTrue((double) result.get("totalCalories") > 0);
        assertTrue((double) result.get("caloriesPerServing") > 0);

        verify(receptRepository, times(1)).findByIdje("R001");
    }

    @Test
    void testCalculateNutritionalValuesByIngredients_InvalidServings() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                receptService.calculateNutritionalValuesByIngredients("R001", 0)
        );

        assertEquals("Število porcij mora biti vsaj 1.", exception.getMessage());
    }

    @Test
    void testCalculateNutritionalValuesByIngredients_RecipeNotFound() {
        when(receptRepository.findByIdje("INVALID")).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () ->
                receptService.calculateNutritionalValuesByIngredients("INVALID", 4)
        );

        assertEquals("Recept ni najden.", exception.getMessage());
    }
}
