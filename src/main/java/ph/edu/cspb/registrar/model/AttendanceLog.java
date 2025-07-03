package ph.edu.cspb.registrar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "attendance_logs", schema = "school")
public class AttendanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "scan_time")
    private OffsetDateTime scanTime;

    @Column(name = "event_type")
    private String eventType;

    @PrePersist
    public void prePersist() {
        if (scanTime == null) {
            scanTime = OffsetDateTime.now();
        }
    }
}
