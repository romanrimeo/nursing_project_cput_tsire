package za.ac.cput.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import za.ac.cput.domain.*;
import za.ac.cput.repository.PlacementRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlacementServiceTest {

    private final PlacementRepository repository = Mockito.mock(PlacementRepository.class);
    private final PlacementService service = new PlacementService(repository);

    @Test
    void testCreatePlacement() {
        Student student = new Student();
        Facility facility = new Facility();
        Staff staff = new Staff();

        Placement placement = Placement.builder()
                .student(student)
                .facility(facility)
                .staff(staff)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .status(PlacementStatus.PLACED)
                .studentPreference(true)
                .build();

        when(repository.save(placement)).thenReturn(placement);

        Placement saved = service.create(placement);
        assertEquals(PlacementStatus.PLACED, saved.getStatus());
        verify(repository, times(1)).save(placement);
    }

    @Test
    void testReadPlacement() {
        Placement placement = Placement.builder()
                .placementId(1L)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(placement));

        Placement found = service.read(1L);
        assertNotNull(found);
        assertEquals(1L, found.getPlacementId());
    }
}
