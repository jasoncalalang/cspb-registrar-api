package ph.edu.cspb.registrar.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record StudentDto(
        Long id,
        String lrn,
        String lastName,
        String firstName,
        String middleName,
        String extensionName,
        LocalDate birthDate,
        String birthPlace,
        String gender,
        String nationality,
        String religion,
        Short numSiblings,
        String siblingNames,
        String imgPath,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
