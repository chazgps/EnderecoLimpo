package formatadores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FmtAdicionaAcentuacaoTest {
    FmtAdicionaAcentuacao formatador;

    @Before
    public void setUp() {
        formatador = new FmtAdicionaAcentuacao();
    }

    @Test
    public void testAdicionaAcentos() {
        String endereco = "Rua AluISIO Sa e Moises,68";
        String resultadoEsperado = "Rua AluÍSIO Sá e Moisés,68";

        String resultado = formatador.formatar(endereco);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testReferenciaNula() {
        String endereco = null;

        formatador.formatar(endereco);
    }
}