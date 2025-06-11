package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Evaluacion;
import unlu.sip.pga.dto.EvaluacionDTO;

@Mapper(componentModel = "spring")
public interface EvaluacionMapper {
    EvaluacionMapper INSTANCE = Mappers.getMapper(EvaluacionMapper.class);
    @Mapping(source = "curso.id", target = "idCurso")
    @Mapping(source = "evaluacionTests", target = "tests")
    EvaluacionDTO toDto(Evaluacion e);

    @Mapping(source = "idCurso", target = "curso.id")
    Evaluacion toEntity(EvaluacionDTO dto);

}