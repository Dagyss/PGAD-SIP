package unlu.sip.pga.services.impl;

import org.springframework.stereotype.Service;
import unlu.sip.pga.entities.Curso;
import unlu.sip.pga.services.CursoService;
import unlu.sip.pga.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {
    @Autowired private CursoRepository cursoRepository;
    public Curso crearCurso(Curso curso) { return cursoRepository.save(curso); }
    public Optional<Curso> obtenerCursoPorId(Long id) { return cursoRepository.findById(id); }
    public List<Curso> listarCursos() { return cursoRepository.findAll(); }
    public Curso actualizarCurso(Curso curso) { return cursoRepository.save(curso); }
    public void eliminarCurso(Long id) { cursoRepository.deleteById(id); }
}