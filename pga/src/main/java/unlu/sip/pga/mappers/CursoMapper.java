package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Curso;
import unlu.sip.pga.dto.CursoDTO;

@Mapper(componentModel = "spring")
public interface CursoMapper {
    CursoMapper INSTANCE = Mappers.getMapper(CursoMapper.class);
    Curso toEntity(CursoDTO dto);
    CursoDTO toDto(Curso entity);
}
