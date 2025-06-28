package ph.edu.cspb.registrar;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ph.edu.cspb.registrar.model.Student;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class StudentValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void invalidLrnReturnsViolation() {
        Student student = new Student();
        student.setLrn("BADLRN");
        student.setLastName("Doe");
        student.setFirstName("John");
        student.setBirthDate(LocalDate.of(2000,1,1));

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("lrn"));
    }
}
