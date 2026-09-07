package za.ac.cput.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import za.ac.cput.domain.Facility;
import za.ac.cput.domain.FacilityType;
import za.ac.cput.repository.FacilityRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacilityServiceTest {

    private final FacilityRepository repository = Mockito.mock(FacilityRepository.class);
    private final FacilityService service = new FacilityService(repository);

    @Test
    void testReadFacility() {
        Facility facility = Facility.builder()
                .facilityId(1L)
                .name("Test Facility")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(facility));

        Facility found = service.read(1L);

        assertNotNull(found);
        assertEquals(1L, found.getFacilityId());
    }

    @Test
    void testCreateFacility() {
        Facility facility = Facility.builder()
                .name("Clinic A")
                .type(FacilityType.CLINIC)
                .build();

        when(repository.save(facility)).thenReturn(facility);

        Facility saved = service.create(facility);

        assertEquals("Clinic A", saved.getName());
        verify(repository, times(1)).save(facility);
    }
}
