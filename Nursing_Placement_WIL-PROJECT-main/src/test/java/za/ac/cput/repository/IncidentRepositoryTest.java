package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IncidentRepositoryTest {

    @Autowired
    private IncidentRepository repository;

    @Test
    void testSaveAndFind() {
        Incident incident = Incident.builder()
                .student(new Student())
                .facility(new Facility())
                .reportedByStaff(new Staff())
                .date(LocalDate.now())
                .description("Test incident")
                .discipline("Midwifery")
                .status(IncidentStatus.OPEN)
                .build();

        Incident saved = repository.save(incident);
        assertNotNull(saved.getIncidentId());

        Incident found = repository.findById(saved.getIncidentId()).orElse(null);
        assertNotNull(found);
        assertEquals("Midwifery", found.getDiscipline());
    }
}
