package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unlu.sip.pga.entities.Pago;
import unlu.sip.pga.dto.PagoDTO;
import unlu.sip.pga.services.UsuarioService;

@Mapper(componentModel = "spring", uses = { UsuarioService.class })
public interface PagoMapper {
    @Mapping(target = "usuario", expression = "java(usuarioService.obtenerUsuarioPorId(dto.getUsuarioId()).orElse(null))")
    Pago toEntity(PagoDTO dto);

    @Mapping(source = "usuario.idUsuario", target = "usuarioId")
    PagoDTO toDto(Pago entity);
}