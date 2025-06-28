package ph.edu.cspb.registrar.mapper;

import ph.edu.cspb.registrar.dto.StudentDto;
import ph.edu.cspb.registrar.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toDto(Student student);

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "requirements", ignore = true)
    Student toEntity(StudentDto dto);
}
