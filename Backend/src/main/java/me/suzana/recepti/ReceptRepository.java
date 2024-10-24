package me.suzana.recepti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptRepository extends JpaRepository<Recept, Long> {
    Recept findByIdje(String idje);
}
