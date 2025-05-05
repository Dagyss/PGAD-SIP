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
    protected UsuarioService usuarioService;

    @Mapping(target = "usuario", expression = "java(usuarioService.obtenerUsuarioPorId(dto.getUsuario().getId()).orElse(null))")
    public abstract Pago toEntity(PagoDTO dto);

    @Mapping(source = "usuario", target = "usuario")
    public abstract PagoDTO toDto(Pago entity);

    @Named("mapUsuarioIdToUsuario")
    Usuario mapUsuarioIdToUsuario(String usuarioId) {
        return usuarioService.obtenerUsuarioPorId(usuarioId).orElse(null);
    }
}
