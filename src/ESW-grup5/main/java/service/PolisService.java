package service;

import java.util.List;
import model.Polis;
import repository.PolisRepository;

public class PolisService {

    private PolisRepository polisRepository;

    public PolisService(PolisRepository polisRepository) {
        this.polisRepository = polisRepository;
    }

    public List<Polis> getAllPolis() {
        return polisRepository.findAll();
    }
}