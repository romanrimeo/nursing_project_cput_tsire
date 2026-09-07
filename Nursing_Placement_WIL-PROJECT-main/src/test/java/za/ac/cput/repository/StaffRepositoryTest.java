package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.Staff;
import za.ac.cput.domain.StaffRole;
import za.ac.cput.factory.StaffFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StaffRepositoryTest {

    @Autowired
    private StaffRepository staffRepository;

    @Test
    void testSaveAndFindByEmail() {
        Staff staff = StaffFactory.createStaff(
                "John Smith",
                "john@cput.ac.za",
                "password123",
                StaffRole.CLINICAL_STAFF,
                3
        );

        staffRepository.save(staff);

        Optional<Staff> found = staffRepository.findByEmail("john@cput.ac.za");
        assertTrue(found.isPresent());
        assertEquals("John Smith", found.get().getName());
    }

    @Test
    void testExistsByEmail() {
        Staff staff = StaffFactory.createStaff(
                "Mary",
                "mary@cput.ac.za",
                "pass",
                StaffRole.ADMIN,
                2
        );

        staffRepository.save(staff);

        assertTrue(staffRepository.existsByEmail("mary@cput.ac.za"));
        assertFalse(staffRepository.existsByEmail("unknown@cput.ac.za"));
    }

    @Test
    void testDeleteByEmail() {
        Staff staff = StaffFactory.createStaff(
                "Alex",
                "alex@cput.ac.za",
                "1234",
                StaffRole.HOD,
                4
        );

        staffRepository.save(staff);

        staffRepository.deleteByEmail("alex@cput.ac.za");

        assertFalse(staffRepository.existsByEmail("alex@cput.ac.za"));
    }
}
