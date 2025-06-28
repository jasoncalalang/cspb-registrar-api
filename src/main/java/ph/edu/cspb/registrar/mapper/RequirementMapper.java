package ph.edu.cspb.registrar.mapper;

import ph.edu.cspb.registrar.dto.RequirementDto;
import ph.edu.cspb.registrar.model.Requirement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequirementMapper {
    @Mapping(source = "requirementType.id", target = "requirementTypeId")
    RequirementDto toDto(Requirement r);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "requirementType", ignore = true)
    Requirement toEntity(RequirementDto dto);
}
