package ph.edu.cspb.registrar.mapper;

import org.junit.jupiter.api.Test;
import ph.edu.cspb.registrar.dto.RequirementDto;
import ph.edu.cspb.registrar.model.Requirement;
import ph.edu.cspb.registrar.model.RequirementId;
import ph.edu.cspb.registrar.model.RequirementType;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class RequirementMapperTest {
    private final RequirementMapper mapper = Mappers.getMapper(RequirementMapper.class);

    @Test
    void toDtoAndBack() {
        Requirement req = new Requirement();
        RequirementType type = new RequirementType();
        type.setId((short)1);
        req.setRequirementType(type);
        req.setId(new RequirementId(2L, (short)1));
        req.setSubmitted(true);
        req.setSubmittedDate(LocalDate.of(2024,1,1));

        RequirementDto dto = mapper.toDto(req);
        assertThat(dto.requirementTypeId()).isEqualTo((short)1);
        assertThat(dto.submitted()).isTrue();

        Requirement back = mapper.toEntity(dto);
        assertThat(back.isSubmitted()).isEqualTo(req.isSubmitted());
        assertThat(back.getSubmittedDate()).isEqualTo(req.getSubmittedDate());
    }
}
