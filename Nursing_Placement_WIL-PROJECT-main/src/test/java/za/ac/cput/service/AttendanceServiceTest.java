package za.ac.cput.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import za.ac.cput.domain.Attendance;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.Placement;
import za.ac.cput.repository.AttendanceRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendanceServiceTest {

    private final AttendanceRepository repository = Mockito.mock(AttendanceRepository.class);
    private final AttendanceService service = new AttendanceService(repository);

    @Test
    void testCreateAttendance() {
        Student student = new Student();
        Placement placement = new Placement();

        Attendance attendance = Attendance.builder()
                .student(student)
                .placement(placement)
                .date(LocalDate.now())
                .checkInTime(LocalTime.of(8, 0))
                .checkOutTime(LocalTime.of(16, 0))
                .locationMap("GPS")
                .hoursWorked(8.0)
                .build();

        when(repository.save(attendance)).thenReturn(attendance);

        Attendance saved = service.create(attendance);

        assertEquals(8.0, saved.getHoursWorked());
        verify(repository, times(1)).save(attendance);
    }

    @Test
    void testReadAttendance() {
        Attendance attendance = Attendance.builder()
                .attendanceId(1L)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(attendance));

        Attendance found = service.read(1L);

        assertNotNull(found);
        assertEquals(1L, found.getAttendanceId());
    }
}
