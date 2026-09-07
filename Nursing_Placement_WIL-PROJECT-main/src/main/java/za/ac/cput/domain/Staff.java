package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "staff")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;

    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private StaffRole role;
    private Integer yearLevelAssigned;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<Placement> placements;

    @OneToMany(mappedBy = "reportedByStaff", cascade = CascadeType.ALL)
    private List<Incident> incidentReports;
}
