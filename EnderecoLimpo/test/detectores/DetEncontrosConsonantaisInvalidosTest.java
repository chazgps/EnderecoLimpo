package detectores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetEncontrosConsonantaisInvalidosTest {

    IDetector detector;

    @Before
    public void setUp() {
        detector = new DetEncontrosConsonantaisInvalidos();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaoAceitaArgumentoInvalido() {
        detector.analisar(null);
    }

    @Test
    public void testAnalisar1() {
        int resultado = detector.analisar("Rua Amtonio Siqueira,nº 100");
        int resultadoEsperado = 1;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAnalisar2() {
        int resultado = detector.analisar("Rua Antonio Siqueira,nº 100");
        int resultadoEsperado = 0;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObterOcorrencias() {
        detector.analisar("Rua Amtonio Siqueira,nº 100");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "mt[5,6]";
        assertEquals(resultadoEsperado, resultado);
    }
}
