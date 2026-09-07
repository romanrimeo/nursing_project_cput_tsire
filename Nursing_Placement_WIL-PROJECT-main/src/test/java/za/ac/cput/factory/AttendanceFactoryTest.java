package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Attendance;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.Placement;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceFactoryTest {

    @Test
    void testCreateAttendanceSuccess() {
        Student student = new Student();
        Placement placement = new Placement();

        Attendance attendance = AttendanceFactory.createAttendance(
                student,
                placement,
                LocalDate.now(),
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                "GPS: -33.9249, 18.4241",
                8.0
        );

        assertNotNull(attendance);
        assertEquals(8.0, attendance.getHoursWorked());
        assertEquals("GPS: -33.9249, 18.4241", attendance.getLocationMap());
    }

    @Test
    void testInvalidHoursWorked() {
        Student student = new Student();
        Placement placement = new Placement();

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                AttendanceFactory.createAttendance(
                        student,
                        placement,
                        LocalDate.now(),
                        LocalTime.of(8, 0),
                        LocalTime.of(16, 0),
                        "GPS",
                        -5.0
                )
        );

        assertEquals("Hours worked must be positive", ex.getMessage());
    }
}
