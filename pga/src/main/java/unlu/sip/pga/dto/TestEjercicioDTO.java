package unlu.sip.pga.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestEjercicioDTO {
    private String entrada;

    @JsonAlias({"salidaesperada", "salida_esperada", "output", "resultado", "expected_output", "esperado", "salida"})
    private String salidaEsperada;
}