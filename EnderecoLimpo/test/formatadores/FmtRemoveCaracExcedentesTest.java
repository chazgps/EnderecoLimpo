package formatadores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FmtRemoveCaracExcedentesTest {
    FmtRemoveCaracExcedentes formatador;

    @Before
    public void setUp() {
        formatador = new FmtRemoveCaracExcedentes();
    }

    @Test
    public void testRemoveZeros() {
        String endereco = "Rua Antonio Calmon 00547";
        String resultadoEsperado = "Rua Antonio Calmon 547";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testRemoveEspacos() {
        String endereco = "Rua Antonio   Calmon 00547";
        String resultadoEsperado = "Rua Antonio Calmon 547";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testRemoveExcessoNasPontas() {
        String endereco = "Rua Antonio Calmon 547   ";
        String resultadoEsperado = "Rua Antonio Calmon 547";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testMantemIntacto() {
        String endereco = "Rua Antonio Calmon 547";
        String resultadoEsperado = "Rua Antonio Calmon 547";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testReferenciaNula() {
        String endereco = null;

        formatador.formatar(endereco);
    }
}