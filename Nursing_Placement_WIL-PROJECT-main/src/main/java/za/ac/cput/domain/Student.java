package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "student")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(nullable = false, unique = true)
    private String studentNumber;

    private String firstName;
    private String lastName;
    private int yearLevel;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private StudentStatus status;

    // Relationships
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Placement> placements;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Incident> incidents;
}
