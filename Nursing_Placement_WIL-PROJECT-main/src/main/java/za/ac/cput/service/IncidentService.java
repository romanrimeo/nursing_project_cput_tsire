package za.ac.cput.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Incident;
import za.ac.cput.domain.IncidentStatus;
import za.ac.cput.repository.IncidentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository repository;

    public Incident create(Incident incident) {
        return repository.save(incident);
    }

    public Incident read(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Incident> getByStudent(Long studentId) {
        return repository.findByStudent_StudentId(studentId);
    }

    public List<Incident> getByStatus(IncidentStatus status) {
        return repository.findByStatus(status);
    }

    public List<Incident> getByDiscipline(String discipline) {
        return repository.findByDiscipline(discipline);
    }

    public Incident update(Incident incident) {
        return repository.save(incident);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Incident> getAll() { return repository.findAll(); }
}
