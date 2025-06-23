package unlu.sip.pga.mappers;

import org.mapstruct.*;
import unlu.sip.pga.entities.ResolucionEvaluacion;
import unlu.sip.pga.dto.ResolucionEvaluacionDTO;

@Mapper(componentModel = "spring")
public interface ResolucionEvaluacionMapper {
    ResolucionEvaluacionDTO toDto(ResolucionEvaluacion entity);
    ResolucionEvaluacion toEntity(ResolucionEvaluacionDTO dto);
}