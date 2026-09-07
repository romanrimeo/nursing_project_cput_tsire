package za.ac.cput.factory;

import za.ac.cput.domain.*;

import java.time.LocalDate;

public class PlacementFactory {

    public static Placement createPlacement(Student student,
                                            Facility facility,
                                            Staff staff,
                                            LocalDate startDate,
                                            LocalDate endDate,
                                            PlacementStatus status,
                                            Boolean studentPreference) {

        if (student == null) throw new IllegalArgumentException("Student is required");
        if (facility == null) throw new IllegalArgumentException("Facility is required");
        if (staff == null) throw new IllegalArgumentException("Staff is required");
        if (startDate == null) throw new IllegalArgumentException("Start date is required");
        if (endDate == null) throw new IllegalArgumentException("End date is required");
        if (status == null) throw new IllegalArgumentException("Placement status is required");
        if (studentPreference == null) throw new IllegalArgumentException("Student preference is required");

        return Placement.builder()
                .student(student)
                .facility(facility)
                .staff(staff)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .studentPreference(studentPreference)
                .build();
    }
}
