package ph.edu.cspb.registrar.mapper;

import org.junit.jupiter.api.Test;
import ph.edu.cspb.registrar.dto.StudentDto;
import ph.edu.cspb.registrar.model.Student;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class StudentMapperTest {
    private final StudentMapper mapper = Mappers.getMapper(StudentMapper.class);

    @Test
    void toDtoAndBack() {
        Student entity = new Student();
        entity.setId(1L);
        entity.setLrn("123456789012");
        entity.setLastName("Doe");
        entity.setFirstName("John");
        entity.setBirthDate(LocalDate.of(2000,1,1));

        StudentDto dto = mapper.toDto(entity);
        assertThat(dto.lrn()).isEqualTo("123456789012");

        Student back = mapper.toEntity(dto);
        assertThat(back.getLrn()).isEqualTo(entity.getLrn());
        assertThat(back.getLastName()).isEqualTo(entity.getLastName());
    }
}
