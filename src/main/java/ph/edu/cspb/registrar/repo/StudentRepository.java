package ph.edu.cspb.registrar.repo;

import ph.edu.cspb.registrar.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
}
