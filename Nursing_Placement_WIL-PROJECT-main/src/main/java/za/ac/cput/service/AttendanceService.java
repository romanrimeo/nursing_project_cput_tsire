package za.ac.cput.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Attendance;
import za.ac.cput.repository.AttendanceRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository repository;

    public Attendance create(Attendance attendance) {
        return repository.save(attendance);
    }

    public Attendance read(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Attendance> getByStudent(Long studentId) {
        return repository.findByStudent_StudentId(studentId);
    }

    public List<Attendance> getByPlacement(Long placementId) {
        return repository.findByPlacement_PlacementId(placementId);
    }

    public List<Attendance> getByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public Attendance update(Attendance attendance) {
        return repository.save(attendance);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
