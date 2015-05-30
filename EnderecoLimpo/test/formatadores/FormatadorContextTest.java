package formatadores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FormatadorContextTest {

    FormatadorContext contexto;

    @Before
    public void setUp() {
        contexto = new FormatadorContext();
    }

    @Test (expected=IllegalArgumentException.class)
    public void testAdicionaFormatador() {
        contexto.adicionaFormatador(null);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testRemoveFormatador() {
        contexto.removeFormatador(null);
    }

    @Test
    public void testFormatar() {
        String endereco = "Av Antonio @agripino  da costa, 114";
        String resultadoEsperado = "Av Antonio Agripino da Costa, 114";

        contexto.adicionaFormatador(new FmtMaiusculaSomentePrimeira());
        contexto.adicionaFormatador(new FmtRemoveCaracEstranhos());
        String resultado = contexto.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testReferenciaNula() {
        String endereco = null;

        contexto.formatar(endereco);
    }
}