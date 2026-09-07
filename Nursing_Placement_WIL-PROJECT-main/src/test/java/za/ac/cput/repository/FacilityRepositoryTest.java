package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.Facility;
import za.ac.cput.domain.FacilityType;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FacilityRepositoryTest {

    @Autowired
    private FacilityRepository repository;

    @Test
    void testSaveAndFindFacility() {
        Facility facility = Facility.builder()
                .name("Old Age Home A")
                .type(FacilityType.OLD_AGE)
                .address("123 Sunset Road")
                .contactNumber("0210001234")
                .contactPerson("Mrs Adams")
                .build();

        Facility saved = repository.save(facility);
        assertNotNull(saved.getFacilityId());

        Facility found = repository.findById(saved.getFacilityId()).orElse(null);
        assertNotNull(found);
        assertEquals("Old Age Home A", found.getName());
    }
}
