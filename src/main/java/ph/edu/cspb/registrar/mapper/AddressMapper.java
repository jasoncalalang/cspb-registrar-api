package ph.edu.cspb.registrar.mapper;

import ph.edu.cspb.registrar.dto.AddressDto;
import ph.edu.cspb.registrar.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toDto(Address address);

    @Mapping(target = "studentId", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Address toEntity(AddressDto dto);
}
