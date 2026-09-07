package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "incident")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incidentId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({"incidents", "placements", "attendances", "passwords"})
    private Student student;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff reportedByStaff;

    private LocalDate date;
    private String description;
    private String discipline;
    @Enumerated(EnumType.STRING)
    private IncidentStatus status;
}

