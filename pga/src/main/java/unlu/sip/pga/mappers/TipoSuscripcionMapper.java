package unlu.sip.pga.mappers;

import unlu.sip.pga.dto.TipoSuscripcionDTO;
import unlu.sip.pga.entities.TipoSuscripcion;

public interface TipoSuscripcionMapper {
    TipoSuscripcion toEntity(TipoSuscripcionDTO dto);
    TipoSuscripcionDTO toDto(TipoSuscripcion entity);
}