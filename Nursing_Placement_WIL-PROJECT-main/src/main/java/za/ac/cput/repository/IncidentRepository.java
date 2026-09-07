package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Incident;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    List<Incident> findByStudent_StudentId(Long studentId);

    List<Incident> findByStatus(za.ac.cput.domain.IncidentStatus status);

    List<Incident> findByDiscipline(String discipline);
}
