package ph.edu.cspb.registrar.repo;

import ph.edu.cspb.registrar.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
