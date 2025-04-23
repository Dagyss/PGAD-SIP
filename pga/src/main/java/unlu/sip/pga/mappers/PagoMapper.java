package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
<<<<<<< HEAD
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.entities.Pago;
import unlu.sip.pga.entities.Usuario;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 0492083de115e8cb3e9c38106ea36d0d6cfd3ea9
import unlu.sip.pga.dto.PagoDTO;
import unlu.sip.pga.entities.Pago;
import unlu.sip.pga.services.UsuarioService;

@Mapper(componentModel = "spring")
<<<<<<< HEAD
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
=======
public abstract class PagoMapper {

    @Autowired
    protected UsuarioService usuarioService;

    @Mapping(target = "usuario", expression = "java(usuarioService.obtenerUsuarioPorId(dto.getIdUsuario()).orElse(null))")
    public abstract Pago toEntity(PagoDTO dto);

    @Mapping(source = "usuario.idUsuario", target = "idUsuario")
    public abstract PagoDTO toDto(Pago entity);
}
>>>>>>> 0492083de115e8cb3e9c38106ea36d0d6cfd3ea9
