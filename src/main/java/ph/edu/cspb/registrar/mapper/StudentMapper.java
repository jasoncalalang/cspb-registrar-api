package ph.edu.cspb.registrar.mapper;

import ph.edu.cspb.registrar.dto.StudentDto;
import ph.edu.cspb.registrar.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toDto(Student student);
    Student toEntity(StudentDto dto);
}
