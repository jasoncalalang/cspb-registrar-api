package ph.edu.cspb.registrar.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ph.edu.cspb.registrar.model.AttendanceLog;
import ph.edu.cspb.registrar.model.Student;
import ph.edu.cspb.registrar.repo.AttendanceLogRepository;
import ph.edu.cspb.registrar.repo.StudentRepository;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AttendanceController {

    private final StudentRepository studentRepository;
    private final AttendanceLogRepository attendanceLogRepository;

    public AttendanceController(StudentRepository studentRepository,
                                AttendanceLogRepository attendanceLogRepository) {
        this.studentRepository = studentRepository;
        this.attendanceLogRepository = attendanceLogRepository;
    }

    @PostMapping("/students/{id}/scan")
    public ResponseEntity<Map<String, String>> scan(@PathVariable Long id) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime threshold = now.minusMinutes(5);
        Optional<AttendanceLog> latest = attendanceLogRepository
                .findFirstByStudentIdOrderByScanTimeDesc(id);
        if (latest.isPresent() && latest.get().getScanTime().isAfter(threshold)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "message", "invalid within 5 minute window",
                            "dateTime", now.toString()
                    ));
        }

        String type = latest.map(AttendanceLog::getEventType)
                .map(t -> t.equals("IN") ? "OUT" : "IN")
                .orElse("IN");

        AttendanceLog log = new AttendanceLog();
        log.setStudent(studentOpt.get());
        log.setEventType(type);
        log.setScanTime(now);
        attendanceLogRepository.save(log);

        return ResponseEntity.ok(Map.of(
                "message", "successful",
                "dateTime", now.toString()
        ));
    }
}
