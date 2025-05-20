package unlu.sip.pga.services;

import java.util.List;
import unlu.sip.pga.dto.CategoriaDTO;

public interface CategoriaService {
    CategoriaDTO crear(CategoriaDTO dto);
    CategoriaDTO actualizar(Integer id, CategoriaDTO dto);
    void eliminar(Integer id);
    CategoriaDTO obtenerPorId(Integer id);
    List<CategoriaDTO> listarTodas();
}
