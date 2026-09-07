package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Staff;
import za.ac.cput.domain.StaffRole;

import static org.junit.jupiter.api.Assertions.*;

class StaffFactoryTest {

    @Test
    void testCreateStaffSuccess() {
        Staff staff = StaffFactory.createStaff(
                "John Doe",
                "john.doe@example.com",
                "password123",
                StaffRole.CLINICAL_STAFF,
                3
        );

        assertNotNull(staff);
        assertEquals("John Doe", staff.getName());
        assertEquals("john.doe@example.com", staff.getEmail());
        assertEquals(StaffRole.CLINICAL_STAFF, staff.getRole());
        assertEquals(3, staff.getYearLevelAssigned());
    }

    @Test
    void testCreateStaffInvalidName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                StaffFactory.createStaff(
                        "",
                        "test@example.com",
                        "pass",
                        StaffRole.ADMIN,
                        1
                )
        );

        assertEquals("Name is required", exception.getMessage());
    }

    @Test
    void testCreateStaffInvalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                StaffFactory.createStaff(
                        "Jane Doe",
                        "",
                        "pass",
                        StaffRole.HOD,
                        2
                )
        );

        assertEquals("Email is required", exception.getMessage());
    }

    @Test
    void testCreateStaffInvalidPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                StaffFactory.createStaff(
                        "Jane Doe",
                        "jane@example.com",
                        "",
                        StaffRole.ADMIN,
                        2
                )
        );

        assertEquals("Password is required", exception.getMessage());
    }

    @Test
    void testCreateStaffNullRole() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                StaffFactory.createStaff(
                        "Jane Doe",
                        "jane@example.com",
                        "password",
                        null,
                        2
                )
        );

        assertEquals("Role is required", exception.getMessage());
    }
}
