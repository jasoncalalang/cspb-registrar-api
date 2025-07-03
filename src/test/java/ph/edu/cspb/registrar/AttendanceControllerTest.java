package ph.edu.cspb.registrar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ph.edu.cspb.registrar.api.AttendanceController;
import ph.edu.cspb.registrar.model.AttendanceLog;
import ph.edu.cspb.registrar.model.Student;
import ph.edu.cspb.registrar.repo.AttendanceLogRepository;
import ph.edu.cspb.registrar.repo.StudentRepository;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AttendanceControllerTest {

    private StudentRepository studentRepository;
    private AttendanceLogRepository attendanceLogRepository;
    private AttendanceController controller;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        attendanceLogRepository = mock(AttendanceLogRepository.class);
        controller = new AttendanceController(studentRepository, attendanceLogRepository);
    }

    @Test
    void firstScanCreatesLogAndReturnsOk() {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(attendanceLogRepository.findFirstByStudentIdOrderByScanTimeDesc(1L)).thenReturn(Optional.empty());
        when(attendanceLogRepository.save(any(AttendanceLog.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<Map<String, String>> response = controller.scan(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsEntry("message", "successful");
        verify(attendanceLogRepository).save(any(AttendanceLog.class));
    }

    @Test
    void scanWithinFiveMinutesReturnsBadRequest() {
        Student student = new Student();
        student.setId(1L);
        AttendanceLog log = new AttendanceLog();
        log.setStudent(student);
        log.setEventType("IN");
        log.setScanTime(OffsetDateTime.now().minusMinutes(1));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(attendanceLogRepository.findFirstByStudentIdOrderByScanTimeDesc(1L)).thenReturn(Optional.of(log));

        ResponseEntity<Map<String, String>> response = controller.scan(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(attendanceLogRepository, never()).save(any());
    }
}
