package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Evaluacion;
import unlu.sip.pga.dto.EvaluacionDTO;

@Mapper(componentModel = "spring")
public interface EvaluacionMapper {
    EvaluacionMapper INSTANCE = Mappers.getMapper(EvaluacionMapper.class);
    Evaluacion toEntity(EvaluacionDTO dto);
    EvaluacionDTO toDto(Evaluacion entity);
}
