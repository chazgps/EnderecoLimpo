package detectores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetNomeNumerosTest {

    IDetector detector;

    @Before
    public void setUp() {
        detector = new DetNomeNumeros();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaoAceitaArgumentoInvalido() {
        detector.analisar(null);
    }

    @Test
    public void testAnalisar1() {
        int resultado = detector.analisar("RUA SAO MAURICIO361 APTO 31");
        int resultadoEsperado = 1;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAnalisar2() {
        int resultado = detector.analisar("RUA SAO MAURICIO 361 APTO 31");
        int resultadoEsperado = 0;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObterOcorrencias() {
        detector.analisar("RUA SAO MAURICIO361 APTO 31");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "MAURICIO361[8,18]";
        assertEquals(resultadoEsperado, resultado);
    }
}
