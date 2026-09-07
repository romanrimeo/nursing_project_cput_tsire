package za.ac.cput.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Staff;
import za.ac.cput.service.StaffService;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StaffController {

    private final StaffService service;

    @PostMapping
    public ResponseEntity<Staff> create(@RequestBody Staff staff) {
        return ResponseEntity.ok(service.create(staff));
    }

    // Fixed: Logic updated to accept email instead of Long ID
    @GetMapping("/{email}")
    public ResponseEntity<Staff> read(@PathVariable String email) {
        Staff staff = service.read(email);
        if (staff == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(staff);
    }

    @GetMapping
    public ResponseEntity<List<Staff>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping
    public ResponseEntity<Staff> update(@RequestBody Staff staff) {
        return ResponseEntity.ok(service.update(staff));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}