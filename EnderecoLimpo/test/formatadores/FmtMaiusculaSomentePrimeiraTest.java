package formatadores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FmtMaiusculaSomentePrimeiraTest {
    FmtMaiusculaSomentePrimeira formatador;

    @Before
    public void setUp() {
        formatador = new FmtMaiusculaSomentePrimeira();
    }

    @Test
    public void testMaiscula() {
        String endereco = "Rua Maria JOSEFA doS SANtos 129";
        String resultadoEsperado = "Rua Maria Josefa dos Santos 129";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    public void testTransformaMinusculas() {
        String endereco = "rua maria josefa dos santos 129";
        String resultadoEsperado = "Rua Maria Josefa dos Santos 129";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testReferenciaNula() {
        String endereco = null;

        formatador.formatar(endereco);
    }
}