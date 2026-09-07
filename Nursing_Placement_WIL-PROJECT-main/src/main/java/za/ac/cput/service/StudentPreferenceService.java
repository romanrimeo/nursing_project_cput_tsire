package za.ac.cput.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Facility;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.StudentPreference;
import za.ac.cput.dto.PreferenceRequest;
import za.ac.cput.dto.PreferenceResponse;
import za.ac.cput.repository.FacilityRepository;
import za.ac.cput.repository.StudentPreferenceRepository;
import za.ac.cput.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentPreferenceService {

    private final StudentPreferenceRepository prefRepo;
    private final StudentRepository studentRepo;
    private final FacilityRepository facilityRepo;

    @Transactional
    public List<PreferenceResponse> savePreferences(PreferenceRequest dto) {
        prefRepo.deleteByStudent_StudentNumber(dto.studentNumber()); // clear old

        Student student = studentRepo.findByStudentNumber(dto.studentNumber())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        short rank = 1;
        for (Long facId : dto.facilityIds()) {
            Facility f = facilityRepo.findById(facId)
                    .orElseThrow(() -> new IllegalArgumentException("Facility id=" + facId + " not found"));
            StudentPreference pref = StudentPreference.builder()
                    .student(student)
                    .facility(f)
                    .rank(rank++)
                    .build();
            prefRepo.save(pref);
        }

        return prefRepo.findByStudent_StudentNumberOrderByRank(dto.studentNumber())
                .stream()
                .map(p -> new PreferenceResponse(
                        p.getId(),
                        p.getFacility().getFacilityId(),
                        p.getFacility().getName(),
                        p.getRank(),
                        p.getCreatedAt()))
                .toList();
    }

    public List<PreferenceResponse> getPreferences(String studentNumber) {
        return prefRepo.findByStudent_StudentNumberOrderByRank(studentNumber)
                .stream()
                .map(p -> new PreferenceResponse(
                        p.getId(),
                        p.getFacility().getFacilityId(),
                        p.getFacility().getName(),
                        p.getRank(),
                        p.getCreatedAt()))
                .toList();
    }
}