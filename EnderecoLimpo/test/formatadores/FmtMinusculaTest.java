package formatadores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FmtMinusculaTest {
    FmtMinuscula formatador;

    @Before
    public void setUp() {
        formatador = new FmtMinuscula();
    }

    @Test
    public void testTransformaEmMinuscula() {
        String endereco = "Alameda EstaÇÃO Estacio de Sá 129?";
        String resultadoEsperado = "alameda estação estacio de sá 129?";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testReferenciaNula() {
        String endereco = null;

        formatador.formatar(endereco);
    }
}