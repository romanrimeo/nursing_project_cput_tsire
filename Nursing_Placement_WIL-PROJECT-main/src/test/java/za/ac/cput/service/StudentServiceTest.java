package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.StudentStatus;
import za.ac.cput.factory.StudentFactory;
import za.ac.cput.repository.StudentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentRepository = Mockito.mock(StudentRepository.class);
        studentService = new StudentService(studentRepository);
    }

    @Test
    void testCreateStudentSuccess() {
        Student student = StudentFactory.createStudent(
                "222",
                "Zee",
                "Nxumalo",
                2,
                "zee@example.com",
                "123",
                StudentStatus.ACTIVE
        );

        when(studentRepository.existsByStudentNumber("222")).thenReturn(false);
        when(studentRepository.save(student)).thenReturn(student);

        Student created = studentService.create(student);

        assertNotNull(created);
        assertEquals("Zee", created.getFirstName());
    }

    @Test
    void testCreateStudentDuplicate() {
        Student student = StudentFactory.createStudent(
                "222",
                "Zee",
                "X",
                2,
                "zee@example.com",
                "123",
                StudentStatus.GRADUATED
        );

        when(studentRepository.existsByStudentNumber("222")).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                studentService.create(student)
        );

        assertEquals("Student number already exists.", ex.getMessage());
    }

    @Test
    void testReadStudent() {
        Student student = StudentFactory.createStudent(
                "555",
                "Kate",
                "Brown",
                3,
                "kate@example.com",
                "pass",
                StudentStatus.ACTIVE
        );

        when(studentRepository.findByStudentNumber("555"))
                .thenReturn(Optional.of(student));

        Student found = studentService.readByStudentNumber("555");

        assertNotNull(found);
        assertEquals("Kate", found.getFirstName());
    }

    @Test
    void testUpdateStudent() {
        Student student = StudentFactory.createStudent(
                "999",
                "Tom",
                "Black",
                3,
                "tom@example.com",
                "pass",
                StudentStatus.GRADUATED
        );

        when(studentRepository.existsByStudentNumber("999")).thenReturn(true);
        when(studentRepository.save(student)).thenReturn(student);

        Student updated = studentService.update(student);

        assertEquals("Tom", updated.getFirstName());
    }

    @Test
    void testDeleteStudent() {
        when(studentRepository.existsByStudentNumber("111")).thenReturn(true);
        doNothing().when(studentRepository).deleteByStudentNumber("111");

        assertDoesNotThrow(() -> studentService.deleteByStudentNumber("111"));

        verify(studentRepository, times(1)).deleteByStudentNumber("111");
    }
}
