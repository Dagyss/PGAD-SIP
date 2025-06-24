package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.entities.Pago;
import unlu.sip.pga.entities.TransactionDetails;
import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.dto.PagoDTO;
import unlu.sip.pga.dto.TransactionDetailsDTO;
import unlu.sip.pga.dto.UsuarioDTO;
import unlu.sip.pga.services.UsuarioService;

@Mapper(componentModel = "spring")
@Named("pagoMapper")
public abstract class PagoMapper {
    @Autowired
    protected UsuarioService usuarioService;

    @Mapping(target = "usuario", expression = "java(usuarioService.obtenerUsuarioPorId(dto.getUsuario().getId()).orElse(null))")
    public abstract Pago toEntity(PagoDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "dateCreated", target = "date_created", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Mapping(source = "dateApproved", target = "date_approved", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Mapping(source = "moneyReleaseDate", target = "money_release_date", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "statusDetail", target = "status_detail")
    @Mapping(target = "transaction_details", expression = "java(mapTransactionDetails(entity.getTransactionDetails()))")
    @Mapping(target = "usuario", expression = "java(mapUsuario(entity.getUsuario()))")
    public abstract PagoDTO toDto(Pago entity);

    // @Named("mapUsuarioIdToUsuario")
    // Usuario mapUsuarioIdToUsuario(String usuarioId) {
    //     return usuarioService.obtenerUsuarioPorId(usuarioId).orElse(null);
    // }

    TransactionDetailsDTO mapTransactionDetails(TransactionDetails td) {
        if (td == null) {
            return null;
        }
        TransactionDetailsDTO dto = new TransactionDetailsDTO();
        dto.setNet_received_amount(td.getNetReceivedAmount());
        dto.setTotal_paid_amount(td.getTotalPaidAmount());
        return dto;
    }

    UsuarioDTO mapUsuario(Usuario u) {
        if (u == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setCorreo(u.getCorreo());
        dto.setNivelConocimiento(u.getNivelConocimiento());
        dto.setEstadoCuenta(u.getEstadoCuenta());
        return dto;
    }
}
