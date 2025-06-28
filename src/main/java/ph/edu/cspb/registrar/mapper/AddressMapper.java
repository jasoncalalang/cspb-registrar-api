package ph.edu.cspb.registrar.mapper;

import ph.edu.cspb.registrar.dto.AddressDto;
import ph.edu.cspb.registrar.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toDto(Address address);
    Address toEntity(AddressDto dto);
}
