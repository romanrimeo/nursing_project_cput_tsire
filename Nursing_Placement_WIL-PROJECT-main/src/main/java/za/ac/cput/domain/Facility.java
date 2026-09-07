package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "facility")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    private String name;

    @Enumerated(EnumType.STRING)
    private FacilityType type;

    private String address;
    private String contactNumber;
    private String contactPerson;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<Placement> placements;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<Incident> incidents;
}

