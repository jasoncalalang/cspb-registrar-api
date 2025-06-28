package ph.edu.cspb.registrar.dto;

import java.time.LocalDate;

public record RequirementDto(
        Short requirementTypeId,
        boolean submitted,
        LocalDate submittedDate
) {
}
