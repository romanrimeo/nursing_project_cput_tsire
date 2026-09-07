package za.ac.cput.factory;

import za.ac.cput.domain.Attendance;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.Placement;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceFactory {

    public static Attendance createAttendance(Student student,
                                              Placement placement,
                                              LocalDate date,
                                              LocalTime checkInTime,
                                              LocalTime checkOutTime,
                                              String locationMap,
                                              Double hoursWorked) {

        if (student == null) throw new IllegalArgumentException("Student is required");
        if (placement == null) throw new IllegalArgumentException("Placement is required");
        if (date == null) throw new IllegalArgumentException("Date is required");
        if (checkInTime == null) throw new IllegalArgumentException("Check-in time is required");
        if (checkOutTime == null) throw new IllegalArgumentException("Check-out time is required");
        if (locationMap == null || locationMap.isEmpty()) throw new IllegalArgumentException("Location is required");
        if (hoursWorked == null || hoursWorked < 0) throw new IllegalArgumentException("Hours worked must be positive");

        return Attendance.builder()
                .student(student)
                .placement(placement)
                .date(date)
                .checkInTime(checkInTime)
                .checkOutTime(checkOutTime)
                .locationMap(locationMap)
                .hoursWorked(hoursWorked)
                .build();
    }
}
