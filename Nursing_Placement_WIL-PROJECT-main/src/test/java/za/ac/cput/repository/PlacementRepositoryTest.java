package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PlacementRepositoryTest {

    @Autowired
    private PlacementRepository repository;

    @Test
    void testSaveAndFindByStatus() {
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

        Placement saved = repository.save(placement);
        assertNotNull(saved.getPlacementId());

        List<Placement> list = repository.findByStatus(PlacementStatus.PLACED);
        assertNotNull(list);
    }
}
