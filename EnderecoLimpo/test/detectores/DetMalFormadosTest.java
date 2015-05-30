package detectores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetMalFormadosTest {

    IDetector detector;

    @Before
    public void setUp() {
        detector = new DetMalFormados();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaoAceitaArgumentoInvalido() {
        detector.analisar(null);
    }

    @Test
    public void testAnalisar1() {
        int resultado = detector.analisar("RUA");
        int resultadoEsperado = 1;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAnalisar2() {
        int resultado = detector.analisar("RUA ANTONIO JUPIA S/N");
        int resultadoEsperado = 0;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObterOcorrencias() {
        detector.analisar("RUA");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "RUA[0,2]";
        assertEquals(resultadoEsperado, resultado);
    }
}
