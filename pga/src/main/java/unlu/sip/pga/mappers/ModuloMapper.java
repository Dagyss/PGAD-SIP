package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unlu.sip.pga.entities.Modulo;
import unlu.sip.pga.dto.ModuloDTO;

@Mapper(componentModel = "spring")
public interface ModuloMapper {


    @Mapping(source = "curso.id",      target = "cursoId")
    @Mapping(target = "contenidos",    ignore = true)
    @Mapping(target = "ejercicios",    ignore = true)
    ModuloDTO toDto(Modulo entity);

    @Mapping(target = "curso", ignore = true)
    Modulo toEntity(ModuloDTO dto);
}