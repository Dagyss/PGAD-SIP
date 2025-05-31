package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Modulo;
import unlu.sip.pga.dto.ModuloDTO;

@Mapper(componentModel = "spring", uses = { EjercicioMapper.class })
public interface ModuloMapper {
    ModuloMapper INSTANCE = Mappers.getMapper(ModuloMapper.class);

    @Mapping(source = "curso.id", target = "cursoId")
    @Mapping(target = "contenidos", ignore = true)
    ModuloDTO toDto(Modulo entity);

    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "contenidos", ignore = true)
    @Mapping(target = "ejercicios", ignore = true)
    Modulo toEntity(ModuloDTO dto);
}