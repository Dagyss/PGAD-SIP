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
    @Mapping(source = "modulo.id", target = "moduloId")
    EjercicioDTO toDto(Ejercicio entity);

    // Para toEntity, como antes
    Ejercicio toEntity(EjercicioDTO dto);
}