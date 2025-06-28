package ph.edu.cspb.registrar.mapper;

import org.junit.jupiter.api.Test;
import ph.edu.cspb.registrar.dto.AddressDto;
import ph.edu.cspb.registrar.model.Address;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class AddressMapperTest {
    private final AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    @Test
    void toDtoAndBack() {
        Address address = new Address();
        address.setHouseNo("10A");
        address.setStreetSubd("Main St");
        address.setBgyCode("123456789");

        AddressDto dto = mapper.toDto(address);
        assertThat(dto.houseNo()).isEqualTo("10A");

        Address back = mapper.toEntity(dto);
        assertThat(back.getHouseNo()).isEqualTo(address.getHouseNo());
        assertThat(back.getBgyCode()).isEqualTo(address.getBgyCode());
    }
}
