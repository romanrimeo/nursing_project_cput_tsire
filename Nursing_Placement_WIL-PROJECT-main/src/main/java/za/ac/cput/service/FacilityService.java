package za.ac.cput.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Facility;
import za.ac.cput.domain.FacilityType;
import za.ac.cput.repository.FacilityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository repository;

    public Facility create(Facility facility) {
        return repository.save(facility);
    }

    public Facility read(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Facility> getByType(FacilityType type) {
        return repository.findByType(type);
    }

    public Facility update(Facility facility) {
        return repository.save(facility);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Facility> getAll() { return repository.findAll(); }



}
