package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import unlu.sip.pga.dto.TipoUsuarioDTO;
import unlu.sip.pga.entities.TipoUsuario;

@Mapper(componentModel = "spring")
public interface TipoUsuarioMapper {
    TipoUsuario toEntity(TipoUsuarioDTO dto);
    TipoUsuarioDTO toDto(TipoUsuario entity);
}
