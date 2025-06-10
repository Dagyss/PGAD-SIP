package unlu.sip.pga.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonAlias;
import unlu.sip.pga.entities.TestEjercicio;

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
}
