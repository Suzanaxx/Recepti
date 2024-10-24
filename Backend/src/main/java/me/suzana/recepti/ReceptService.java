package me.suzana.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceptService {

    @Autowired
    private ReceptRepository receptRepository;

    public List<Recept> getAllRecepti() {
        return receptRepository.findAll();
    }

    public Recept getReceptByIdje(String idje) {
        return receptRepository.findByIdje(idje);
    }

    public Recept createRecept(Recept recept) {
        return receptRepository.save(recept);
    }

    public Recept updateRecept(String idje, Recept recept) {
        Recept existingRecept = receptRepository.findByIdje(idje);
        if (existingRecept != null) {
            existingRecept.setIme(recept.getIme());
            existingRecept.setOpis(recept.getOpis());
            existingRecept.setSestavine(recept.getSestavine());
            existingRecept.setNavodila(recept.getNavodila());
            existingRecept.setSlika(recept.getSlika());
            return receptRepository.save(existingRecept);
        }
        return null; // Or throw an exception if not found
    }

    public void deleteRecept(String idje) {
        Recept existingRecept = receptRepository.findByIdje(idje);
        if (existingRecept != null) {
            receptRepository.delete(existingRecept);
        }
    }
}
