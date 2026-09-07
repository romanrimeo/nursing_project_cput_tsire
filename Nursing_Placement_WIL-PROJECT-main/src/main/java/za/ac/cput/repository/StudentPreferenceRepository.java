package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.StudentPreference;

import java.util.List;

public interface StudentPreferenceRepository extends JpaRepository<StudentPreference, Long> {

    List<StudentPreference> findByStudent_StudentNumberOrderByRank(String studentNumber);

    void deleteByStudent_StudentNumber(String studentNumber);
}