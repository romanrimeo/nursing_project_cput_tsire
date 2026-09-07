package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Facility;
import za.ac.cput.domain.FacilityType;

import java.util.List;
import java.util.Optional;


@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long>
        {
            Optional<Facility> findByNameContainingIgnoreCase(String name);
            List<Facility> findByType(FacilityType type);
}
