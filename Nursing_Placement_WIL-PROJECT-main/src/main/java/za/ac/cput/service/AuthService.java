package za.ac.cput.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Staff;
import za.ac.cput.domain.Student;
import za.ac.cput.repository.StaffRepository;
import za.ac.cput.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final StaffRepository staffRepository;

    public Object authenticate(String username, String password, String role) {

        if ("STUDENT".equalsIgnoreCase(role)) {
            Student student = studentRepository.findByStudentNumber(username).orElse(null);
            if (student != null && student.getPassword().equals(password)) {
                return student;
            }
        } else if ("STAFF".equalsIgnoreCase(role)) {
            Staff staff = staffRepository.findByEmail(username).orElse(null);
            if (staff != null && staff.getPassword().equals(password)) {
                return staff;
            }
        }
        throw new IllegalArgumentException("Invalid username, password, or role");
    }
}