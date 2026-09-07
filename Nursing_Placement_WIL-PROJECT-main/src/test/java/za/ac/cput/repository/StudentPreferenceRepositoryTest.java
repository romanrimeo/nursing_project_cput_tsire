package za.ac.cput.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import za.ac.cput.Application;
import za.ac.cput.domain.StudentPreference;
import za.ac.cput.repository.FacilityRepository;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = Application.class)
class StudentPreferenceRepositoryTest {

    @Autowired private StudentPreferenceRepository repo;
    @Autowired
    private FacilityRepository facilityRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Sql({"/insert-student.sql", "/insert-facility.sql"})
    void debugSetup() {
        // Check what facilities exist
        System.out.println("Facilities: " + facilityRepository.findAll());
        // Check what students exist
        System.out.println("Students: " + studentRepository.findAll());
    }

    @Test
    @Sql({"/insert-student.sql", "/insert-facility.sql", "/insert-preferences.sql"})
    void findByStudent_StudentNumberOrderByRank_returnsOrdered() {
        List<StudentPreference> list = repo.findByStudent_StudentNumberOrderByRank("219117675");
        assertThat(list).hasSize(3)
                .extracting(StudentPreference::getRank)
                .containsExactly((short) 1, (short) 2, (short) 3);
    }

    @Test
    @Sql({"/insert-student.sql", "/insert-facility.sql", "/insert-preferences.sql"})
    void deleteByStudent_StudentNumber_removesRows() {
        repo.deleteByStudent_StudentNumber("219117675");
        assertThat(repo.findByStudent_StudentNumberOrderByRank("219117675")).isEmpty();
    }

    @Test
    @Sql({"/insert-student.sql", "/insert-facility.sql"})
    void findByStudent_StudentNumberOrderByRank_noPreferences_returnsEmpty() {
        assertThat(repo.findByStudent_StudentNumberOrderByRank("219117675")).isEmpty();
    }
}