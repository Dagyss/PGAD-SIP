package unlu.sip.pga.mappers;


import org.mapstruct.Mapper;
import unlu.sip.pga.dto.ResolucionDTO;
import unlu.sip.pga.entities.Resolucion;

@Mapper(componentModel = "spring")
public interface ResolucionMapper {
    Resolucion toEntity(ResolucionDTO dto);
    ResolucionDTO toDto(Resolucion entity);
}