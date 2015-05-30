package detectores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetTermosInglesTest {

    IDetector detector;

    @Before
    public void setUp() {
        detector = new DetTermosIngles();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaoAceitaArgumentoInvalido() {
        detector.analisar(null);
    }

    @Test
    public void testAnalisar() {
        int resultado = detector.analisar("AVENUE AM COSTA NUMBER 291-4FLOOR");

        int resultadoEsperado = 1;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObterOcorrencias() {
        detector.analisar("AVENUE AM COSTA NUMBER 291-4FLOOR");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "AVENUE[0,5]NUMBER[16,21]";
        assertEquals(resultadoEsperado, resultado);
    }
}
