package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import unlu.sip.pga.entities.ContenidoModulo;
import unlu.sip.pga.dto.ContenidoModuloDTO;

@Mapper(componentModel = "spring")
public interface ContenidoModuloMapper {
    ContenidoModulo toEntity(ContenidoModuloDTO dto);
    ContenidoModuloDTO toDto(ContenidoModulo entity);
}
