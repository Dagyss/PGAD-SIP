package unlu.sip.pga.mappers;


import org.mapstruct.Mapper;
import unlu.sip.pga.dto.CalificacionDTO;
import unlu.sip.pga.entities.Calificacion;

@Mapper(componentModel = "spring")
public interface CalificacionMapper {
    Calificacion toEntity(CalificacionDTO dto);
    CalificacionDTO toDto(Calificacion entity);
}