package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import unlu.sip.pga.entities.Suscripcion;
import unlu.sip.pga.dto.SuscripcionDTO;

@Mapper(componentModel = "spring")
public interface SuscripcionMapper {
    SuscripcionMapper INSTANCE = Mappers.getMapper(SuscripcionMapper.class);
    Suscripcion toEntity(SuscripcionDTO dto);
    SuscripcionDTO toDto(Suscripcion entity);
}