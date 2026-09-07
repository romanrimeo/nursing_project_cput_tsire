package za.ac.cput.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Facility;
import za.ac.cput.domain.FacilityType;
import za.ac.cput.service.FacilityService;

import java.util.List;

@RestController
@RequestMapping("/api/facility")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FacilityController {

    private final FacilityService service;

    @PostMapping
    public ResponseEntity<Facility> create(@RequestBody Facility facility) {
        return ResponseEntity.ok(service.create(facility));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facility> read(@PathVariable Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Facility>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping
    public ResponseEntity<Facility> update(@RequestBody Facility facility) {
        return ResponseEntity.ok(service.update(facility));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
