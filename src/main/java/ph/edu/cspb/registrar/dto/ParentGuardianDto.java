package ph.edu.cspb.registrar.dto;

public record ParentGuardianDto(
        String role,
        String firstName,
        String lastName,
        String middleName,
        String occupation,
        String contactNum,
        String email,
        String highestEducationalAttainment
) {
}
