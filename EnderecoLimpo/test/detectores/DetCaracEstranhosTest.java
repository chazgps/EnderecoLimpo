package detectores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetCaracEstranhosTest {

    IDetector detector;

    @Before
    public void setUp() {
        detector = new DetCaracEstranhos();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaoAceitaArgumentoInvalido() {
        detector.analisar(null);
    }

    @Test
    public void testAnalisar() {
        int resultado = detector.analisar("RUA SEÑOR ABRAVANEL£ 317");
        int resultadoEsperado = 1;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObterOcorrencias() {
        detector.analisar("RUA SEÑOR ABRAVANEL£ 317");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "Ñ[6,6]/£[19,19]";
        assertEquals(resultadoEsperado, resultado);
    }
}
