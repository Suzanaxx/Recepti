package me.suzana.recepti;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
}
