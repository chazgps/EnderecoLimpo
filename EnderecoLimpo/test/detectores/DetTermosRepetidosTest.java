package detectores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetTermosRepetidosTest {

    IDetector detector;

    @Before
    public void setUp() {
        detector = new DetTermosRepetidos();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaoAceitaArgumentoInvalido() {
        detector.analisar(null);
    }

    @Test
    public void testAnalisar1() {
        int resultado = detector.analisar("Rua r dos jasmins 258");
        int resultadoEsperado = 1;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAnalisar2() {
        int resultado = detector.analisar("Rua dos jasmins 258");
        int resultadoEsperado = 0;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObterOcorrencias() {
        detector.analisar("Rua r dos jasmins 258");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "Rua r[0,4]";
        assertEquals(resultadoEsperado, resultado);
    }
}
