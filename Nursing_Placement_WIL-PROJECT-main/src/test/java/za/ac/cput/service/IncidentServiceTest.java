package za.ac.cput.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import za.ac.cput.domain.*;
import za.ac.cput.repository.IncidentRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IncidentServiceTest {

    private final IncidentRepository repository = Mockito.mock(IncidentRepository.class);
    private final IncidentService service = new IncidentService(repository);

    @Test
    void testRead() {
        Incident incident = Incident.builder()
                .incidentId(1L)
                .description("Test")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(incident));

        Incident found = service.read(1L);

        assertNotNull(found);
        assertEquals(1L, found.getIncidentId());
    }

    @Test
    void testCreate() {
        Incident incident = Incident.builder()
                .description("Created")
                .build();

        when(repository.save(incident)).thenReturn(incident);

        Incident saved = service.create(incident);

        assertEquals("Created", saved.getDescription());
        verify(repository, times(1)).save(incident);
    }
}
