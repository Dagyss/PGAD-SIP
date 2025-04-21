package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Ejercicio;
import unlu.sip.pga.services.EjercicioService;
import unlu.sip.pga.repositories.EjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EjercicioServiceImpl implements EjercicioService {
    @Autowired private EjercicioRepository ejercicioRepository;
    public Ejercicio crearEjercicio(Ejercicio ejercicio) { return ejercicioRepository.save(ejercicio); }
    public Optional<Ejercicio> obtenerEjercicioPorId(Integer id) { return ejercicioRepository.findById(id); }
    public List<Ejercicio> listarEjerciciosPorModulo(Integer moduloId) { return ejercicioRepository.findByModuloId(moduloId); }
    public Ejercicio actualizarEjercicio(Ejercicio ejercicio) { return ejercicioRepository.save(ejercicio); }
    public void eliminarEjercicio(Integer id) { ejercicioRepository.deleteById(id); }
}