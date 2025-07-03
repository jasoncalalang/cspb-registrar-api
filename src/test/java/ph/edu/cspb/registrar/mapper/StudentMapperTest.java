package ph.edu.cspb.registrar.mapper;

import org.junit.jupiter.api.Test;
import ph.edu.cspb.registrar.dto.StudentDto;
import ph.edu.cspb.registrar.model.Student;
import ph.edu.cspb.registrar.model.ParentGuardian;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class StudentMapperTest {
    private final StudentMapper mapper = Mappers.getMapper(StudentMapper.class);

    {
        try {
            java.lang.reflect.Field f = mapper.getClass().getDeclaredField("parentGuardianMapper");
            f.setAccessible(true);
            f.set(mapper, Mappers.getMapper(ParentGuardianMapper.class));
        } catch (Exception ignored) {
        }
    }

    @Test
    void toDtoAndBack() {
        Student entity = new Student();
        entity.setId(1L);
        entity.setLrn("123456789012");
        entity.setLastName("Doe");
        entity.setFirstName("John");
        entity.setBirthDate(LocalDate.of(2000,1,1));

        ParentGuardian parent = new ParentGuardian();
        parent.setRole("father");
        parent.setFirstName("Jack");
        parent.setLastName("Doe");
        parent.setStudent(entity);
        entity.getParents().add(parent);

        StudentDto dto = mapper.toDto(entity);
        assertThat(dto.lrn()).isEqualTo("123456789012");
        assertThat(dto.parents()).hasSize(1);

        Student back = mapper.toEntity(dto);
        assertThat(back.getLrn()).isEqualTo(entity.getLrn());
        assertThat(back.getLastName()).isEqualTo(entity.getLastName());
        assertThat(back.getParents()).isEmpty();
    }
}
