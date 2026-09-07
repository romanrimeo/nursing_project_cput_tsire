package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "placement_id")
    private Placement placement;

    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String locationMap; // GPS coordinate
    private Double hoursWorked;
}
