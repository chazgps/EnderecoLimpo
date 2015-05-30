package formatadores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FmtMaiusculaTest {
    FmtMaiuscula formatador;

    @Before
    public void setUp() {
        formatador = new FmtMaiuscula();
    }

    @Test
    public void testTransformaEmMaiscula() {
        String endereco = "Alameda Estação estÁcio de Sá 129 #fundos";
        String resultadoEsperado = "ALAMEDA ESTAÇÃO ESTÁCIO DE SÁ 129 #FUNDOS";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testReferenciaNula() {
        String endereco = null;

        formatador.formatar(endereco);
    }
}