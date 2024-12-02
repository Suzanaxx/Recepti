package me.suzana.recepti;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceptServiceCommentTest {

    private ReceptService receptService;
    private CommentRepository commentRepository;
    private ReceptRepository receptRepository;
    private RatingRepository ratingRepository;

    @BeforeEach
    void setUp() {
        // Mockiranje odvisnosti
        commentRepository = mock(CommentRepository.class);
        receptRepository = mock(ReceptRepository.class);
        ratingRepository = mock(RatingRepository.class);

        // Inicializacija storitve z uporabo vse potrebne odvisnosti
        receptService = new ReceptService(receptRepository, ratingRepository, commentRepository);
    }

    @Test
    @DisplayName("Dodajanje komentarja - uspešen primer")
    void testAddComment_Success() {
        // Priprava podatkov
        Recept recept = new Recept();
        recept.setId(1L);

        Comment comment = new Comment();
        comment.setRecept(recept);
        comment.setUserId(1L);
        comment.setComment("Odličen recept!");

        when(receptRepository.findById(1L)).thenReturn(Optional.of(recept));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Klic metode
        Comment result = receptService.addComment(1L, 1L, "Odličen recept!");

        // Preverjanje rezultatov
        assertNotNull(result, "Komentar naj ne bo null.");
        assertEquals("Odličen recept!", result.getComment(), "Vsebina komentarja mora biti enaka.");
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("Dodajanje komentarja - recept ne obstaja")
    void testAddComment_ReceptNotFound() {
        // Priprava podatkov
        when(receptRepository.findById(1L)).thenReturn(Optional.empty());

        // Preverjanje izjeme
        Exception exception = assertThrows(RuntimeException.class, () ->
                receptService.addComment(1L, 1L, "Komentar")
        );

        assertEquals("Recipe not found", exception.getMessage());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    @DisplayName("Pridobivanje komentarjev za recept - uspešen primer")
    void testFindCommentsByReceptId_Success() {
        // Priprava podatkov
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setRecept(new Recept());
        comment1.setComment("Prvi komentar.");
        comments.add(comment1);

        Comment comment2 = new Comment();
        comment2.setRecept(new Recept());
        comment2.setComment("Drugi komentar.");
        comments.add(comment2);

        when(commentRepository.findByRecept_Id(1L)).thenReturn(comments);

        // Klic metode
        List<Comment> result = receptService.findCommentsByReceptId(1L);

        // Preverjanje rezultatov
        assertNotNull(result, "Seznam komentarjev naj ne bo null.");
        assertEquals(2, result.size(), "Seznam komentarjev mora vsebovati dva elementa.");
        assertEquals("Prvi komentar.", result.get(0).getComment(), "Prvi komentar mora biti pravilen.");
        assertEquals("Drugi komentar.", result.get(1).getComment(), "Drugi komentar mora biti pravilen.");
    }

    @Test
    @DisplayName("Pridobivanje komentarjev za recept - brez komentarjev")
    void testFindCommentsByReceptId_NoComments() {
        // Priprava podatkov
        when(commentRepository.findByRecept_Id(1L)).thenReturn(new ArrayList<>());

        // Klic metode
        List<Comment> result = receptService.findCommentsByReceptId(1L);

        // Preverjanje rezultatov
        assertNotNull(result, "Seznam komentarjev naj ne bo null.");
        assertTrue(result.isEmpty(), "Seznam komentarjev mora biti prazen.");
    }
}
