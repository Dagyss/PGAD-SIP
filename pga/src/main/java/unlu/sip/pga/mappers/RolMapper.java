package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Rol;
import unlu.sip.pga.dto.RolDTO;

@Mapper(componentModel = "spring")
public interface RolMapper {
    RolMapper INSTANCE = Mappers.getMapper(RolMapper.class);
    Rol toEntity(RolDTO dto);
    RolDTO toDto(Rol entity);
}
