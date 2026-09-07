package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "placement")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placementId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({"placements", "attendances", "incidents", "password"})
    private Student student;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    @JsonIgnoreProperties({"placements", "incidents"})
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    @JsonIgnoreProperties({"placements", "incidentReports", "password"})
    private Staff staff;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private PlacementStatus status;

    private Boolean studentPreference;
}

