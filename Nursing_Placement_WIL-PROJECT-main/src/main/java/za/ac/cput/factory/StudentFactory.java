package za.ac.cput.factory;

import za.ac.cput.domain.Student;
import za.ac.cput.domain.StudentStatus;

public class StudentFactory {

    public static Student createStudent(
            String studentNumber,
            String firstName,
            String lastName,
            int yearLevel,
            String email,
            String password,
            StudentStatus status
    ) {

        // -------- VALIDATIONS --------//
        if (studentNumber == null || studentNumber.isEmpty())
            throw new IllegalArgumentException("Student number is required.");

        if (firstName == null || firstName.isEmpty())
            throw new IllegalArgumentException("First name is required.");

        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Email is required.");

        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password is required.");

        if (yearLevel < 1)
            throw new IllegalArgumentException("Year level must be 1 or higher.");

        if (status == null)
            throw new IllegalArgumentException("Student status is required");

        // -------- OBJECT CREATION --------
        return Student.builder()
                .studentNumber(studentNumber)
                .firstName(firstName)
                .lastName(lastName)
                .yearLevel(yearLevel)
                .email(email)
                .password(password)
                .status(status)
                .build();
    }
}
