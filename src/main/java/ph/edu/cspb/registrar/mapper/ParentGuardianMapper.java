package ph.edu.cspb.registrar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ph.edu.cspb.registrar.dto.ParentGuardianDto;
import ph.edu.cspb.registrar.model.ParentGuardian;

@Mapper(componentModel = "spring")
public interface ParentGuardianMapper {
    ParentGuardianDto toDto(ParentGuardian entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ParentGuardian toEntity(ParentGuardianDto dto);
}
