package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Ejercicio;
import unlu.sip.pga.dto.EjercicioDTO;

@Mapper(componentModel = "spring")
public interface EjercicioMapper {
    EjercicioMapper INSTANCE = Mappers.getMapper(EjercicioMapper.class);
    Ejercicio toEntity(EjercicioDTO dto);
    EjercicioDTO toDto(Ejercicio entity);
}
