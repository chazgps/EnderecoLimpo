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
        String endereco = "Alameda Esta��O Estacio de S� 129?";
        String resultadoEsperado = "alameda esta��o estacio de s� 129?";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testReferenciaNula() {
        String endereco = null;

        formatador.formatar(endereco);
    }
}