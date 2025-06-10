package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Curso;
import unlu.sip.pga.dto.CursoDTO;

@Mapper(componentModel = "spring", uses = {EvaluacionMapper.class, ModuloMapper.class, CategoriaMapper.class })
public interface CursoMapper {
    CursoMapper INSTANCE = Mappers.getMapper(CursoMapper.class);

    @Mapping(target = "modulos", source = "modulos")
    @Mapping(target = "categorias", source = "categorias")
    CursoDTO toDto(Curso entity);

    Curso toEntity(CursoDTO dto);
}