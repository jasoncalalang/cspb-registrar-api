package ph.edu.cspb.registrar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ph.edu.cspb.registrar.model.ParentGuardian;

import java.util.List;
import java.util.Optional;

public interface ParentGuardianRepository extends JpaRepository<ParentGuardian, Long> {
    List<ParentGuardian> findByStudentId(Long studentId);
    Optional<ParentGuardian> findByStudentIdAndRole(Long studentId, String role);
}
