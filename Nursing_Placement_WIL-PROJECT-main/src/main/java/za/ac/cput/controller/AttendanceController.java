package za.ac.cput.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Attendance;
import za.ac.cput.service.AttendanceService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService service;

    @PostMapping
    public ResponseEntity<Attendance> create(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(service.create(attendance));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> read(@PathVariable Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.getByStudent(studentId));
    }

    @GetMapping("/placement/{placementId}")
    public ResponseEntity<List<Attendance>> getByPlacement(@PathVariable Long placementId) {
        return ResponseEntity.ok(service.getByPlacement(placementId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Attendance>> getByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(service.getByDate(date));
    }

    @PutMapping
    public ResponseEntity<Attendance> update(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(service.update(attendance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
