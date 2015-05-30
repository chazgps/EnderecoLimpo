package detectores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetExcessoEspacosTest {

    IDetector detector;

    @Before
    public void setUp() {
        detector = new DetExcessoEspacos();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaoAceitaArgumentoInvalido() {
        detector.analisar(null);
    }

    @Test
    public void testAnalisar1() {
        int resultado = detector.analisar("Est.Vidal  Setubal,km 98");
        int resultadoEsperado = 1;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAnalisar2() {
        int resultado = detector.analisar("Est.Vidal Setubal,km 98");
        int resultadoEsperado = 0;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObterOcorrencias() {
        detector.analisar("Est.Vidal  Setubal,km 98");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "  [9,10]";
        assertEquals(resultadoEsperado, resultado);
    }
}
