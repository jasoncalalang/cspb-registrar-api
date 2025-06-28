package ph.edu.cspb.registrar.mapper;

import ph.edu.cspb.registrar.dto.RequirementTypeDto;
import ph.edu.cspb.registrar.model.RequirementType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequirementTypeMapper {
    RequirementTypeDto toDto(RequirementType type);
}
