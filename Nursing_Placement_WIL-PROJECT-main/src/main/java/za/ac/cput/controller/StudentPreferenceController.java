package za.ac.cput.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.dto.PreferenceRequest;
import za.ac.cput.dto.PreferenceResponse;
import za.ac.cput.repository.StudentPreferenceRepository;
import za.ac.cput.service.StudentPreferenceService;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentPreferenceController {

    private final StudentPreferenceService service;
    private final StudentPreferenceRepository studentPreferenceRepository;

    @PostMapping
    public ResponseEntity<List<PreferenceResponse>> save(
            @Valid @RequestBody PreferenceRequest dto) {
        List<PreferenceResponse> resp = service.savePreferences(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/{studentNumber}")
    public ResponseEntity<List<PreferenceResponse>> get(
            @PathVariable String studentNumber) {
        return ResponseEntity.ok(service.getPreferences(studentNumber));
    }
    @DeleteMapping("/{studentNumber}")
    public ResponseEntity<Void> deletePreference(@PathVariable String studentNumber) {
        studentPreferenceRepository.deleteByStudent_StudentNumber(studentNumber);
        return ResponseEntity.noContent().build();
    }
}