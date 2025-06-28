package ph.edu.cspb.registrar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "requirements", schema = "school")
public class Requirement {
    @EmbeddedId
    private RequirementId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("requirementTypeId")
    @JoinColumn(name = "requirement_type")
    private RequirementType requirementType;

    private boolean submitted = false;

    @Column(name = "submitted_date")
    private LocalDate submittedDate;
}
