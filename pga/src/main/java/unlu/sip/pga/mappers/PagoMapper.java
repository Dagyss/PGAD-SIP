package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.dto.PagoDTO;
import unlu.sip.pga.entities.Pago;
import unlu.sip.pga.services.UsuarioService;

@Mapper(componentModel = "spring")
public abstract class PagoMapper {

    @Autowired
    protected UsuarioService usuarioService;

    @Mapping(target = "usuario", expression = "java(usuarioService.obtenerUsuarioPorId(dto.getIdUsuario()).orElse(null))")
    public abstract Pago toEntity(PagoDTO dto);

    @Mapping(source = "usuario.idUsuario", target = "idUsuario")
    public abstract PagoDTO toDto(Pago entity);
}
