package za.ac.cput.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Placement;
import za.ac.cput.domain.PlacementStatus;
import za.ac.cput.repository.PlacementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlacementService {

    private final PlacementRepository repository;

    public Placement create(Placement placement) {
        return repository.save(placement);
    }

    public Placement read(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Placement> getByStudent(Long studentId) {
        return repository.findByStudent_StudentId(studentId);
    }

    public List<Placement> getByStatus(PlacementStatus status) {
        return repository.findByStatus(status);
    }

    public List<Placement> getByFacility(Long facilityId) {
        return repository.findByFacility_FacilityId(facilityId);
    }

    public Placement update(Placement placement) {
        return repository.save(placement);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Placement> getAll() {return repository.findAll();}
}
