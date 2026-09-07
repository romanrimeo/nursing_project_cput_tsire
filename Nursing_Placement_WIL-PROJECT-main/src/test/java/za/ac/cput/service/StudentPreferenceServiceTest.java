package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ac.cput.domain.Facility;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.StudentPreference;
import za.ac.cput.dto.PreferenceRequest;
import za.ac.cput.dto.PreferenceResponse;
import za.ac.cput.repository.FacilityRepository;
import za.ac.cput.repository.StudentPreferenceRepository;
import za.ac.cput.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)

class StudentPreferenceServiceTest {

    @Mock private StudentPreferenceRepository prefRepo;
    @Mock private StudentRepository studentRepo;
    @Mock private FacilityRepository facilityRepo;

    @InjectMocks private StudentPreferenceService service;

    private final Student student = Student.builder().studentId(1L).build();
    private final Facility f1 = Facility.builder().facilityId(10L).name("Life").build();
    private final Facility f2 = Facility.builder().facilityId(20L).name("Groote").build();


    @Test
    void savePreferences_success() {

        var dto = new PreferenceRequest("219117675", List.of(10L, 20L));
        given(studentRepo.findByStudentNumber("219117675")).willReturn(Optional.of(student));
        given(facilityRepo.findById(10L)).willReturn(Optional.of(f1));
        given(facilityRepo.findById(20L)).willReturn(Optional.of(f2));

        var prefs = List.of(pref(1L, f1, (short) 1), pref(2L, f2, (short) 2));
        given(prefRepo.findByStudent_StudentNumberOrderByRank("219117675")).willReturn(prefs);

        List<PreferenceResponse> resp = service.savePreferences(dto);

        assertThat(resp).hasSize(2)
                .extracting(PreferenceResponse::facilityName)
                .containsExactly("Life", "Groote");
        then(prefRepo).should().deleteByStudent_StudentNumber("219117675");
        then(prefRepo).should(times(2)).save(any(StudentPreference.class));
    }

    @Test
    void savePreferences_studentNotFound_throws() {
        given(studentRepo.findByStudentNumber("999")).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.savePreferences(new PreferenceRequest("999", List.of(1L))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student not found");
    }

    @Test
    void savePreferences_facilityNotFound_throws() {
        given(studentRepo.findByStudentNumber("219117675")).willReturn(Optional.of(student));
        given(facilityRepo.findById(99L)).willReturn(Optional.empty());

        var dto = new PreferenceRequest("219117675", List.of(99L));
        assertThatThrownBy(() -> service.savePreferences(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Facility id=99 not found");
    }

    @Test
    void getPreferences_success() {
        var prefs = List.of(pref(1L, f1, (short) 1), pref(2L, f2, (short) 2));
        given(prefRepo.findByStudent_StudentNumberOrderByRank("219117675")).willReturn(prefs);

        var result = service.getPreferences("219117675");

        assertThat(result).hasSize(2)
                .extracting(PreferenceResponse::rank)
                .containsExactly((short) 1, (short) 2);
    }

    @Test
    void getPreferences_emptyList() {
        given(prefRepo.findByStudent_StudentNumberOrderByRank("219117675")).willReturn(List.of());
        assertThat(service.getPreferences("219117675")).isEmpty();
    }

    private StudentPreference pref(Long id, Facility f, short rank) {
        return StudentPreference.builder()
                .id(id).student(student).facility(f).rank(rank)
                .createdAt(LocalDateTime.now()).build();
    }
}