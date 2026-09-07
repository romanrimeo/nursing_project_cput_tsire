package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.StudentStatus;

import static org.junit.jupiter.api.Assertions.*;

public class StudentFactoryTest {
    @Test
    void testCreateStudentSuccess() {
        Student student = StudentFactory.createStudent(
                "222634383",
                "Olwethu",
                "Tshingo",
                3,
                "olwethu@example.com",
                "12345",
                StudentStatus.ACTIVE
        );

        assertNotNull(student);
        assertEquals("222634383", student.getStudentNumber());
        assertEquals("active", student.getStatus());
    }

    @Test
    void testCreateStudentMissingStudentNumber() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                StudentFactory.createStudent(
                        "",
                        "James",
                        "Doe",
                        2,
                        "test@example.com",
                        "pass",
                        StudentStatus.SUSPENDED
                )
        );

        assertEquals("Student number is required", ex.getMessage());
    }
}
