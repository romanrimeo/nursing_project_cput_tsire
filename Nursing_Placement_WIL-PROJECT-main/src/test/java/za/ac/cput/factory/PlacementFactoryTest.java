package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PlacementFactoryTest {

    @Test
    void testCreatePlacementSuccess() {
        Student student = new Student();
        Facility facility = new Facility();
        Staff staff = new Staff();

        Placement placement = PlacementFactory.createPlacement(
                student,
                facility,
                staff,
                LocalDate.now(),
                LocalDate.now().plusMonths(3),
                PlacementStatus.PLACED,
                true
        );

        assertNotNull(placement);
        assertEquals(PlacementStatus.PLACED, placement.getStatus());
        assertTrue(placement.getStudentPreference());
    }

    @Test
    void testCreatePlacementNullStatus() {
        Student student = new Student();
        Facility facility = new Facility();
        Staff staff = new Staff();

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                PlacementFactory.createPlacement(
                        student,
                        facility,
                        staff,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(3),
                        null,
                        true
                )
        );

        assertEquals("Placement status is required", ex.getMessage());
    }
}
