package ph.edu.cspb.registrar.dto;

import java.time.LocalDate;
import java.util.List;

public record StudentCreateDto(
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
        AddressDto address,
        List<RequirementDto> requirements,
        List<ParentGuardianDto> parents
) {}
