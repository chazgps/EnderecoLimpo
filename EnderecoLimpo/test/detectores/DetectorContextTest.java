package detectores;

import org.junit.Test;
import static org.junit.Assert.*;

public class DetectorContextTest {

    DetectorContext contexto;

    public DetectorContextTest() {
        contexto = new DetectorContext();
    }

    @Test
    public void testPreparaNovaMedicao() {
        contexto.preparaNovaMedicao();

        long resultado = contexto.obtemResultado(null);
        long resultadoEsperado = 0;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAnalisar() {
        IDetector det = new DetLetrasRepetidas();
        contexto.adicionaDetector(det);
        contexto.preparaNovaMedicao();
        contexto.analisar("Av Asssis Chateubriand 200, Mooca");

        long resultado = contexto.obtemResultado(det);
        long resultadoEsperado = 1;

        assertEquals(resultadoEsperado, resultado);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testReferenciaNula() {
        String endereco=null;
        contexto.analisar(endereco);
    }

    @Test
    public void testObtemResultado() {
        long resultado = contexto.obtemResultado(null);
        long resultadoEsperado = 0;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdicionaDetector() {
        contexto.adicionaDetector(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDetector() {
        contexto.removeDetector(null);
    }
}
