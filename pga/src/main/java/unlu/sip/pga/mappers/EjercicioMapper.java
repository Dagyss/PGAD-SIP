package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unlu.sip.pga.entities.Ejercicio;
import unlu.sip.pga.dto.EjercicioDTO;
import unlu.sip.pga.mappers.CategoriaMapper;
import unlu.sip.pga.mappers.ModuloMapper;

@Mapper(
        componentModel = "spring",
        uses = { ModuloMapper.class, CategoriaMapper.class }
)
public interface EjercicioMapper {
    // Al mapear a DTO, delego en ModuloMapper, pero ignoro explícitamente sus colecciones:
    @Mapping(target = "modulo.contenidos", ignore = true)
    @Mapping(target = "modulo.ejercicios", ignore = true)
    EjercicioDTO toDto(Ejercicio entity);

    // Para toEntity, como antes
    @Mapping(target = "modulo", ignore = true)
    Ejercicio toEntity(EjercicioDTO dto);
}