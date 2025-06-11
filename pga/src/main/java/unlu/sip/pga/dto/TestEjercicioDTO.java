package unlu.sip.pga.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonAlias;
import unlu.sip.pga.entities.TestEjercicio;
import unlu.sip.pga.entities.TestEvaluacion;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestEjercicioDTO {
    private String entrada;

    @JsonAlias({"salidaesperada", "salida_esperada", "output", "resultado", "expected_output", "esperado", "salida"})
    private String salidaEsperada;

    // 👇 Constructor adicional para construir a partir de la entidad TestEjercicio
    public TestEjercicioDTO(TestEjercicio test) {
        this.entrada = test.getEntrada();
        this.salidaEsperada = test.getSalidaEsperada(); 
    }

    public TestEjercicioDTO(TestEvaluacion testEvaluacion) {
        this.entrada = testEvaluacion.getEntrada();
        this.salidaEsperada = testEvaluacion.getSalidaEsperada();
    }
}
