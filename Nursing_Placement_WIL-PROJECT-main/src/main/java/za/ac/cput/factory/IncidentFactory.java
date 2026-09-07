package za.ac.cput.factory;

import za.ac.cput.domain.*;
import java.time.LocalDate;

public class IncidentFactory {

    public static Incident createIncident(Student student,
                                          Facility facility,
                                          Staff reportedByStaff, // This can now be null
                                          LocalDate date,
                                          String description,
                                          String discipline,
                                          IncidentStatus status) {

        if (student == null)
            throw new IllegalArgumentException("Student is required");

        if (date == null)
            throw new IllegalArgumentException("Date is required");
        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("Description is required");
        if (discipline == null || discipline.isEmpty())
            throw new IllegalArgumentException("Discipline is required");
        if (status == null)
            throw new IllegalArgumentException("Status is required");

        return Incident.builder()
                .student(student)
                .facility(facility)
                .reportedByStaff(reportedByStaff)
                .date(date)
                .description(description)
                .discipline(discipline)
                .status(status)
                .build();
    }
}