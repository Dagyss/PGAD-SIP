package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Permiso;
import unlu.sip.pga.dto.PermisoDTO;

@Mapper(componentModel = "spring")
public interface PermisoMapper {
    PermisoMapper INSTANCE = Mappers.getMapper(PermisoMapper.class);
    Permiso toEntity(PermisoDTO dto);
    PermisoDTO toDto(Permiso entity);
}