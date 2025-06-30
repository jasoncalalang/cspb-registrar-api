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
@Table(name = "addresses", schema = "school")
public class Address {
    @Id
    private Long studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "house_no")
    private String houseNo;

    @Column(name = "street_subd")
    private String streetSubd;

    @Column(name = "bgy_code", nullable = false)
    private String bgyCode;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = OffsetDateTime.now();
    }
}
