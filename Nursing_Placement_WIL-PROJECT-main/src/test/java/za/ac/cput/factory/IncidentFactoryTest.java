package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class IncidentFactoryTest {

    @Test
    void testCreateIncidentSuccess() {
        Student student = new Student();
        Facility facility = new Facility();
        Staff staff = new Staff();

        Incident incident = IncidentFactory.createIncident(
                student,
                facility,
                staff,
                LocalDate.now(),
                "Student was late for clinical duty",
                "Nursing",
                IncidentStatus.OPEN
        );

        assertNotNull(incident);
        assertEquals("Nursing", incident.getDiscipline());
        assertEquals(IncidentStatus.OPEN, incident.getStatus());
    }

    @Test
    void testInvalidDescription() {
        Student student = new Student();
        Facility facility = new Facility();
        Staff staff = new Staff();

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                IncidentFactory.createIncident(
                        student,
                        facility,
                        staff,
                        LocalDate.now(),
                        "",
                        "Nursing",
                        IncidentStatus.OPEN
                )
        );

        assertEquals("Description is required", ex.getMessage());
    }
}
