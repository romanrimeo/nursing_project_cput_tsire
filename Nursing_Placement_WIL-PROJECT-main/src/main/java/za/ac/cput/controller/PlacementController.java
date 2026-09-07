package za.ac.cput.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Placement;
import za.ac.cput.domain.PlacementStatus;
import za.ac.cput.service.PlacementService;

import java.util.List;

@RestController
@RequestMapping("/api/placement")
@RequiredArgsConstructor
public class PlacementController {

    private final PlacementService service;

    @PostMapping
    public ResponseEntity<Placement> create(@RequestBody Placement placement) {
        return ResponseEntity.ok(service.create(placement));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Placement> read(@PathVariable Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Placement>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.getByStudent(studentId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Placement>> getByStatus(@PathVariable PlacementStatus status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<List<Placement>> getByFacility(@PathVariable Long facilityId) {
        return ResponseEntity.ok(service.getByFacility(facilityId));
    }

    @PutMapping
    public ResponseEntity<Placement> update(@RequestBody Placement placement) {
        return ResponseEntity.ok(service.update(placement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<Placement>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
