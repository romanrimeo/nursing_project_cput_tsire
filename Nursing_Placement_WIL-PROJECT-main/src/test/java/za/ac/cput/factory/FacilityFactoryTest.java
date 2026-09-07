package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Facility;
import za.ac.cput.domain.FacilityType;

import static org.junit.jupiter.api.Assertions.*;

class FacilityFactoryTest {

    @Test
    void createFacility_success() {
        Facility f = FacilityFactory.createFacility(
                "Belleville",
                FacilityType.HOSPITAL,
                "Symphony Way, Bellville South, Cape Town, 7530",
                "021 6700 4000", "Dr. Strand"
        );
        assertNotNull(f);
        assertEquals("Belleville", f.getName());
        assertEquals(FacilityType.HOSPITAL, f.getType());
        assertEquals("Symphony Way, Bellville South, Cape Town, 7530", f.getAddress());
        assertEquals("021 6700 4000", f.getContactNumber());
        assertEquals("Dr. Strand", f.getContactPerson());
        assertNull(f.getFacilityId()); // ID should be null until saved to database
    }

    @Test
    void createFacility_withNullContactPerson_success() {
        Facility f = FacilityFactory.createFacility(
                "Belleville",
                FacilityType.HOSPITAL,
                "Symphony Way, Bellville South, Cape Town, 7530",
                "021 6700 4000", null
        );
        assertNotNull(f);
        assertEquals("Belleville", f.getName());
        assertNull(f.getContactPerson());
    }

    @Test
    void createFacility_withEmptyContactPerson_success() {
        Facility f = FacilityFactory.createFacility(
                "Belleville",
                FacilityType.HOSPITAL,
                "Symphony Way, Bellville South, Cape Town, 7530",
                "021 6700 4000", ""
        );
        assertNotNull(f);
        assertEquals("Belleville", f.getName());
        assertNull(f.getContactPerson());
    }

    @Test
    void createFacility_throws_whenNameMissing() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                FacilityFactory.createFacility("",
                        FacilityType.HOSPITAL, "123 Test St", "021-555-1234", "Dr. Test")
        );
        assertTrue(ex.getMessage().contains("Facility name or address or contact number or contact person is null or empty"));
    }

    @Test
    void createFacility_throws_whenAddressMissing() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                FacilityFactory.createFacility("Test Hospital",
                        FacilityType.HOSPITAL,"", "021-555-1234", "Dr. Test")
        );
        assertTrue(ex.getMessage().contains("Facility name or address or contact number or contact person is null or empty"));
    }

    @Test
    void createFacility_throws_whenContactNumberMissing() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                FacilityFactory.createFacility("Test Hospital", FacilityType.HOSPITAL, "123 Test St", "", "Dr. Test")
        );
        assertTrue(ex.getMessage().contains("Facility name or address or contact number or contact person is null or empty"));
    }

    @Test
    void createFacility_throws_whenAllMandatoryMissing() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                FacilityFactory.createFacility("", FacilityType.HOSPITAL, "", "", "")
        );
        assertTrue(ex.getMessage().contains("Facility name or address or contact number or contact person is null or empty"));
    }

    @Test
    void createFacility_trimsWhitespace() {
        Facility f = FacilityFactory.createFacility(
                "  Belleville  ",
                FacilityType.CLINIC,
                "  Symphony Way, Bellville South, Cape Town, 7530  ",
                "  021 6700 4000  ", "  Dr. Strand  "
        );
        assertEquals("Belleville", f.getName());
        assertEquals(FacilityType.CLINIC, f.getType());
        assertEquals("Symphony Way, Bellville South, Cape Town, 7530", f.getAddress());
        assertEquals("021 6700 4000", f.getContactNumber());
        assertEquals("Dr. Strand", f.getContactPerson());
    }
}