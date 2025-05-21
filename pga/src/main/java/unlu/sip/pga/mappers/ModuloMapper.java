package unlu.sip.pga.mappers;

import org.mapstruct.*;
import unlu.sip.pga.entities.Modulo;
import unlu.sip.pga.dto.ModuloDTO;

@Mapper(componentModel = "spring")
public interface ModuloMapper {

    @Mapping(target = "curso", ignore = true)
    Modulo toEntity(ModuloDTO dto);

    @Mapping(source = "curso.id", target = "cursoId")
    ModuloDTO toDto(Modulo entity);
}