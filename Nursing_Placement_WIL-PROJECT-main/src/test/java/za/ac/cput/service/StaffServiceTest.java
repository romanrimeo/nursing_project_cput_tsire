package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import za.ac.cput.domain.Staff;
import za.ac.cput.domain.StaffRole;
import za.ac.cput.factory.StaffFactory;
import za.ac.cput.repository.StaffRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffServiceTest {

    private StaffRepository staffRepository;
    private StaffService staffService;

    @BeforeEach
    void setUp() {
        staffRepository = Mockito.mock(StaffRepository.class);
        staffService = new StaffService(staffRepository);
    }

    @Test
    void testCreateStaff() {
        Staff staff = StaffFactory.createStaff(
                "Zee",
                "zee@cput.ac.za",
                "pass",
                StaffRole.ADMIN,
                1
        );

        when(staffRepository.existsByEmail("zee@cput.ac.za")).thenReturn(false);
        when(staffRepository.save(staff)).thenReturn(staff);

        Staff created = staffService.create(staff);

        assertNotNull(created);
        assertEquals("Zee", created.getName());
    }

    @Test
    void testCreateStaffDuplicate() {
        Staff staff = StaffFactory.createStaff(
                "Zee",
                "zee@cput.ac.za",
                "pass",
                StaffRole.ADMIN,
                1
        );

        when(staffRepository.existsByEmail("zee@cput.ac.za")).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                staffService.create(staff)
        );

        assertEquals("Staff email already exists.", ex.getMessage());
    }

    @Test
    void testReadStaff() {
        Staff staff = StaffFactory.createStaff(
                "Kate",
                "kate@cput.ac.za",
                "pass",
                StaffRole.CLINICAL_STAFF,
                2
        );

        when(staffRepository.findByEmail("kate@cput.ac.za"))
                .thenReturn(Optional.of(staff));

        Staff found = staffService.read("kate@cput.ac.za");

        assertNotNull(found);
        assertEquals("Kate", found.getName());
    }

    @Test
    void testDeleteStaff() {
        when(staffRepository.existsByEmail("alex@cput.ac.za")).thenReturn(true);
        doNothing().when(staffRepository).deleteByEmail("alex@cput.ac.za");

        assertDoesNotThrow(() -> staffService.delete("alex@cput.ac.za"));

        verify(staffRepository, times(1)).deleteByEmail("alex@cput.ac.za");
    }
}
