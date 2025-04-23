package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.entities.Pago;
import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.dto.PagoDTO;
import unlu.sip.pga.services.UsuarioService;

@Mapper(componentModel = "spring")
@Named("pagoMapper")
public abstract class PagoMapper {
    @Autowired
    UsuarioService usuarioService;
    
    @Mapping(target = "usuario", source = "usuarioId", qualifiedByName = "mapUsuarioIdToUsuario")
    public abstract Pago toEntity(PagoDTO dto);

    @Mapping(source = "usuario.id", target = "usuarioId")
    public abstract PagoDTO toDto(Pago entity);


    @Named("mapUsuarioIdToUsuario")
    Usuario mapUsuarioIdToUsuario(Integer usuarioId) {
        return usuarioService.obtenerUsuarioPorId(usuarioId).orElse(null);
    }
}
