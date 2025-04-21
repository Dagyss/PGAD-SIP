package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.dto.UsuarioDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
    Usuario toEntity(UsuarioDTO dto);
    UsuarioDTO toDto(Usuario entity);
}
