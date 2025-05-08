package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import unlu.sip.pga.dto.TipoSuscripcionDTO;
import unlu.sip.pga.entities.TipoSuscripcion;

@Mapper(componentModel = "spring")
public interface TipoSuscripcionMapper {
    TipoSuscripcionMapper INSTANCE = Mappers.getMapper(TipoSuscripcionMapper.class);
    TipoSuscripcion toEntity(TipoSuscripcionDTO dto);
    TipoSuscripcionDTO toDto(TipoSuscripcion entity);
}