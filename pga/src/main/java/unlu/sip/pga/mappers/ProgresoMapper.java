package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Progreso;
import unlu.sip.pga.dto.ProgresoDTO;

@Mapper(componentModel = "spring")
public interface ProgresoMapper {
    ProgresoMapper INSTANCE = Mappers.getMapper(ProgresoMapper.class);
    Progreso toEntity(ProgresoDTO dto);
    ProgresoDTO toDto(Progreso entity);
}
