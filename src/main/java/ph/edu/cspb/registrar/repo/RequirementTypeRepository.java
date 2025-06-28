package ph.edu.cspb.registrar.repo;

import ph.edu.cspb.registrar.model.RequirementType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementTypeRepository extends JpaRepository<RequirementType, Short> {
}
