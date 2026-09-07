package za.ac.cput.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Staff;
import za.ac.cput.repository.StaffRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    public Staff create(Staff staff) {
        if (staffRepository.existsByEmail(staff.getEmail())) {
            throw new IllegalArgumentException("Staff email already exists.");
        }
        return staffRepository.save(staff);
    }

    public Staff read(String email) {
        return staffRepository.findByEmail(email)
                .orElse(null);
    }

    public List<Staff> getAll() {
        return staffRepository.findAll();
    }

    public Staff update(Staff staff) {
        if (!staffRepository.existsByEmail(staff.getEmail())) {
            throw new IllegalArgumentException("Staff does not exist.");
        }
        return staffRepository.save(staff);
    }

    public void delete(Long id) {
        staffRepository.deleteById(id);
    }
}