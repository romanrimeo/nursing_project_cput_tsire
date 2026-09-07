package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.StudentStatus;
import za.ac.cput.factory.StudentFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testSaveAndFindByStudentNumber() {
        Student student = StudentFactory.createStudent(
                "222634383",
                "Olwethu",
                "Tshingo",
                3,
                "olwethu@example.com",
                "pass123",
                StudentStatus.ACTIVE
        );

        studentRepository.save(student);

        Optional<Student> found = studentRepository.findByStudentNumber("222634383");

        assertTrue(found.isPresent());
        assertEquals("Olwethu", found.get().getFirstName());
    }

    @Test
    void testExistsByStudentNumber() {
        Student student = StudentFactory.createStudent(
                "1234",
                "John",
                "Doe",
                1,
                "john@example.com",
                "pass",
                StudentStatus.ACTIVE
        );

        studentRepository.save(student);

        assertTrue(studentRepository.existsByStudentNumber("1234"));
        assertFalse(studentRepository.existsByStudentNumber("9999"));
    }

    @Test
    void testDeleteByStudentNumber() {
        Student student = StudentFactory.createStudent(
                "5555",
                "Mary",
                "Smith",
                2,
                "mary@example.com",
                "pass",
                StudentStatus.ACTIVE
        );

        studentRepository.save(student);

        studentRepository.deleteByStudentNumber("5555");

        assertFalse(studentRepository.existsByStudentNumber("5555"));
    }
}