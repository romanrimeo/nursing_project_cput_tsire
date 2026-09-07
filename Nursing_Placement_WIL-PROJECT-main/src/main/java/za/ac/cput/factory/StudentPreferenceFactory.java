package za.ac.cput.factory;

import za.ac.cput.domain.Student;
import za.ac.cput.domain.Facility;
import za.ac.cput.domain.StudentPreference;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;

public class StudentPreferenceFactory {

    public static StudentPreference createPreference(Student student, Facility facility, short rank) {
        if (student == null || facility == null) {
            throw new IllegalArgumentException("Student and Facility are required");
        }
        if (rank <= 0) {
            throw new IllegalArgumentException("Rank must be positive");
        }

        return StudentPreference.builder()
                .student(student)
                .facility(facility)
                .rank(rank)
                .createdAt(LocalDateTime.now())
                .build();
    }
}