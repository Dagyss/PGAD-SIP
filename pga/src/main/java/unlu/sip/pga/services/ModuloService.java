package unlu.sip.pga.services;

import unlu.sip.pga.dto.ModuloDTO;
import unlu.sip.pga.entities.Modulo;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para operaciones CRUD y negocio sobre la entidad Módulo usando DTO.
 */
public interface ModuloService {
    /**
     * Crea un nuevo Módulo a partir de su DTO.
     * @param dto Datos del módulo a crear.
     * @return Entidad Módulo persistida.
     */
    Modulo crearModulo(ModuloDTO dto);

    /**
     * Obtiene un Módulo por su ID.
     * @param id Identificador del módulo.
     * @return Optional con la entidad, si existe.
     */
    Optional<Modulo> obtenerModuloPorId(Integer id);

    /**
     * Lista todos los módulos de un curso.
     * @param cursoId ID del curso.
     * @return Lista de módulos.
     */
    List<Modulo> listarModulosPorCurso(Integer cursoId);

    /**
     * Actualiza un Módulo existente usando su DTO.
     * @param dto Datos del módulo a actualizar.
     * @return Entidad Módulo actualizada.
     */
    Modulo actualizarModulo(ModuloDTO dto);

    /**
     * Elimina un Módulo por su ID.
     * @param id Identificador del módulo a eliminar.
     */
    void eliminarModulo(Integer id);
}
