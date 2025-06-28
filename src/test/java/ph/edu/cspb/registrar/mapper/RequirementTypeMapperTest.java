package ph.edu.cspb.registrar.mapper;

import org.junit.jupiter.api.Test;
import ph.edu.cspb.registrar.dto.RequirementTypeDto;
import ph.edu.cspb.registrar.model.RequirementType;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class RequirementTypeMapperTest {
    private final RequirementTypeMapper mapper = Mappers.getMapper(RequirementTypeMapper.class);

    @Test
    void toDto() {
        RequirementType type = new RequirementType();
        type.setId((short)7);
        type.setName("Doc");

        RequirementTypeDto dto = mapper.toDto(type);
        assertThat(dto.id()).isEqualTo((short)7);
        assertThat(dto.name()).isEqualTo("Doc");
    }
}
