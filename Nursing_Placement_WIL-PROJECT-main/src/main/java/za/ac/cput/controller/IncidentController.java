package za.ac.cput.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Incident;
import za.ac.cput.domain.IncidentStatus;
import za.ac.cput.service.IncidentService;

import java.util.List;

@RestController
@RequestMapping("/api/incident")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class IncidentController {

    private final IncidentService service;

    @PostMapping
    public ResponseEntity<Incident> create(@RequestBody Incident incident) {
        return ResponseEntity.ok(service.create(incident));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> read(@PathVariable Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    @GetMapping
    public ResponseEntity<List<Incident>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
    // ------------------------

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Incident>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.getByStudent(studentId));
    }

    @PutMapping
    public ResponseEntity<Incident> update(@RequestBody Incident incident) {
        return ResponseEntity.ok(service.update(incident));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}