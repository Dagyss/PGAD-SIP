package unlu.sip.pga.mappers;

import org.mapstruct.*;
import unlu.sip.pga.entities.Categoria;
import unlu.sip.pga.dto.CategoriaDTO;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaDTO toDto(Categoria entity);
    Categoria toEntity(CategoriaDTO dto);
}