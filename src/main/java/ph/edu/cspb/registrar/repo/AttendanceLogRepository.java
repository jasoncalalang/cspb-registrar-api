package ph.edu.cspb.registrar.repo;

import ph.edu.cspb.registrar.model.AttendanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Long> {
    Optional<AttendanceLog> findFirstByStudentIdOrderByScanTimeDesc(Long studentId);
}
