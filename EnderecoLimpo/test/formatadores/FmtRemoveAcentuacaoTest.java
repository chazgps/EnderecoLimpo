package formatadores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FmtRemoveAcentuacaoTest {
    FmtRemoveAcentuacao formatador;

    @Before
    public void setUp() {
        formatador = new FmtRemoveAcentuacao();
    }

    @Test
    public void testRemove() {
        String endereco = "Alameda Esta«√O Estacio de S· 129?";
        String resultadoEsperado = "Alameda EstaCAO Estacio de Sa 129?";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testReferenciaNula() {
        String endereco = null;

        formatador.formatar(endereco);
    }
}