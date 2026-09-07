package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Attendance;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudent_StudentId(Long studentId);

    List<Attendance> findByPlacement_PlacementId(Long placementId);

    List<Attendance> findByDate(LocalDate date);
}
