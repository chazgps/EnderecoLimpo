package formatadores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FmtPadronizaPatenteTest {
    FmtPadronizaPatente formatador;

    @Before
    public void setUp() {
        formatador = new FmtPadronizaPatente();
    }

    @Test
    public void testPatente1() {
        String endereco = "Rua Dr.Andre Miranda e Costa,256-Centro";
        String resultadoEsperado = "Rua doutor Andre Miranda e Costa,256-Centro";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    public void testPatente2() {
        String endereco = "Rua Dr Andre Miranda e Costa,256-Centro";
        String resultadoEsperado = "Rua doutor Andre Miranda e Costa,256-Centro";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testReferenciaNula() {
        String endereco = null;

        formatador.formatar(endereco);
    }
}