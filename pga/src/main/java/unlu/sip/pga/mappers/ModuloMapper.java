package unlu.sip.pga.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import unlu.sip.pga.entities.Modulo;
import unlu.sip.pga.dto.ModuloDTO;

@Mapper(componentModel = "spring")
public interface ModuloMapper {
    ModuloMapper INSTANCE = Mappers.getMapper(ModuloMapper.class);
    Modulo toEntity(ModuloDTO dto);
    ModuloDTO toDto(Modulo entity);
}