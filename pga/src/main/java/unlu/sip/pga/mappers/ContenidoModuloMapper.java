package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.ContenidoModulo;
import unlu.sip.pga.dto.ContenidoModuloDTO;

@Mapper(componentModel = "spring")
public interface ContenidoModuloMapper {
    ContenidoModuloMapper INSTANCE = Mappers.getMapper(ContenidoModuloMapper.class);
    ContenidoModulo toEntity(ContenidoModuloDTO dto);
    ContenidoModuloDTO toDto(ContenidoModulo entity);
}
