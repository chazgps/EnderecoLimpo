package formatadores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FmtRemoveCaracEstranhosTest {
    FmtRemoveCaracEstranhos formatador;

    @Before
    public void setUp() {
        formatador = new FmtRemoveCaracEstranhos();
    }

    @Test
    public void testRemove() {
        String endereco = "Rua #Augusto de Sá?? e Souza?";
        String resultadoEsperado = "Rua Augusto de Sá e Souza";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testReferenciaNula() {
        String endereco = null;

        formatador.formatar(endereco);
    }
}