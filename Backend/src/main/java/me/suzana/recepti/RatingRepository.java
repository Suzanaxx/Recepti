package me.suzana.recepti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRecept_Id(Long receptId);
    Optional<Rating> findByRecept_IdAndUserId(Long receptId, Long userId);
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.recept.id = :receptId")
    Double findAverageRatingByRecipeId(Long receptId);
}
