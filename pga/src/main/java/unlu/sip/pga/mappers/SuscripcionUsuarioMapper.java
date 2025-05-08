package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import unlu.sip.pga.dto.SuscripcionUsuarioDTO;
import unlu.sip.pga.entities.SuscripcionUsuario;


@Mapper(componentModel = "spring")
public interface SuscripcionUsuarioMapper {

    SuscripcionUsuario toEntity(SuscripcionUsuarioDTO dto);
    SuscripcionUsuarioDTO toDto(SuscripcionUsuario entity);
}
