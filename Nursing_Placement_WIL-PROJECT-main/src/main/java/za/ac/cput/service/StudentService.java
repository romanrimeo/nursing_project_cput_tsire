package za.ac.cput.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Student;
import za.ac.cput.repository.StudentPreferenceRepository;
import za.ac.cput.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentPreferenceRepository studentPreferenceRepository;

    public Student create(Student student) {
        if (studentRepository.existsByStudentNumber(student.getStudentNumber())) {
            throw new IllegalArgumentException("Student number already exists.");
        }
        return studentRepository.save(student);
    }

    public Student readByStudentNumber(String studentNumber) {
        return studentRepository.findByStudentNumber(studentNumber)
                .orElse(null);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student update(Student student) {
        if (!studentRepository.existsByStudentNumber(student.getStudentNumber())) {
            throw new IllegalArgumentException("Student does not exist.");
        }
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteByStudentNumber(String studentNumber) {
        if (!studentRepository.existsByStudentNumber(studentNumber)) {
            throw new IllegalArgumentException("Student does not exist.");
        }
        studentPreferenceRepository.deleteByStudent_StudentNumber(studentNumber);

        studentRepository.deleteByStudentNumber(studentNumber);
    }
}

