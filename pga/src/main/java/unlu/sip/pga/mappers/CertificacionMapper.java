package unlu.sip.pga.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Certificacion;
import unlu.sip.pga.dto.CertificacionDTO;

@Mapper(componentModel = "spring")
public interface CertificacionMapper {
    CertificacionMapper INSTANCE = Mappers.getMapper(CertificacionMapper.class);
    Certificacion toEntity(CertificacionDTO dto);
    CertificacionDTO toDto(Certificacion entity);
}