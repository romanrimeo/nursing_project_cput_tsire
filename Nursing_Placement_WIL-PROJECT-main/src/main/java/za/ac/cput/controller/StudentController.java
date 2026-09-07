package za.ac.cput.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Student;
import za.ac.cput.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService service;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return ResponseEntity.ok(service.create(student));
    }

    @GetMapping("/{studentNumber}")
    public ResponseEntity<Student> read(@PathVariable String studentNumber) {
        Student student = service.readByStudentNumber(studentNumber);
        if (student == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        return ResponseEntity.ok(service.update(student));
    }

    @DeleteMapping("/{studentNumber}")
    public ResponseEntity<Void> delete(@PathVariable String studentNumber) {
        service.deleteByStudentNumber(studentNumber);
        return ResponseEntity.noContent().build();
    }
}