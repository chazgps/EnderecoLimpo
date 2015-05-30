package detectores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetLetrasRepetidasTest {

    IDetector detector;

    @Before
    public void setUp() {
        detector = new DetLetrasRepetidas();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaoAceitaArgumentoInvalido() {
        detector.analisar(null);
    }

    @Test
    public void testAnalisar1() {
        int resultado = detector.analisar("Rua dos jjasmins 258");
        int resultadoEsperado = 1;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAnalisar2() {
        int resultado = detector.analisar("Rua dos jassmins 258");
        int resultadoEsperado = 0;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObterOcorrencias() {
        detector.analisar("Rua dos jjasmins 258");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "jj[8,9]";
        assertEquals(resultadoEsperado, resultado);
    }
}
