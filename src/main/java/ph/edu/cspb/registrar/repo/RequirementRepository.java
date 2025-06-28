package ph.edu.cspb.registrar.repo;

import ph.edu.cspb.registrar.model.Requirement;
import ph.edu.cspb.registrar.model.RequirementId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepository extends JpaRepository<Requirement, RequirementId> {
}
