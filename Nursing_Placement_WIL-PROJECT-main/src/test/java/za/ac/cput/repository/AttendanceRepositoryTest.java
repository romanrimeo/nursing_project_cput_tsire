package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.Attendance;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.Placement;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository repository;

    @Test
    void testSaveAndFindByStudentId() {
        Student student = new Student();
        Placement placement = new Placement();

        Attendance attendance = Attendance.builder()
                .student(student)
                .placement(placement)
                .date(LocalDate.now())
                .checkInTime(LocalTime.of(8, 0))
                .checkOutTime(LocalTime.of(16, 0))
                .locationMap("GPS: -33.9249, 18.4241")
                .hoursWorked(8.0)
                .build();

        Attendance saved = repository.save(attendance);

        List<Attendance> list = repository.findByStudent_StudentId(student.getStudentId());
        assertNotNull(list);
    }
}
