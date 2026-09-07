package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Placement;
import za.ac.cput.domain.PlacementStatus;

import java.util.List;

@Repository
public interface PlacementRepository extends JpaRepository<Placement, Long> {

    List<Placement> findByStudent_StudentId(Long studentId);

    List<Placement> findByStatus(PlacementStatus status);

    List<Placement> findByFacility_FacilityId(Long facilityId);
}
