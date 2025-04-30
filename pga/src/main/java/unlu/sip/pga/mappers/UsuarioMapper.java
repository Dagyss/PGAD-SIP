package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unlu.sip.pga.dto.Auth0UserDTO;
import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.dto.UsuarioDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDTO dto);
    UsuarioDTO toDto(Usuario entity);

    @Mapping(source = "userId",        target = "id")
    @Mapping(source = "name",          target = "nombre")
    @Mapping(source = "email",         target = "correo")
    @Mapping(source = "emailVerified", target = "estadoCuenta")
    Usuario fromAuth0(Auth0UserDTO auth0);
}