package detectores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetNomesAcentuadosErroneamenteTest {

    IDetector detector;

    @Before
    public void setUp() {
        detector = new DetNomesAcentuadosErroneamente();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaoAceitaArgumentoInvalido() {
        detector.analisar(null);
    }

    @Test
    public void testAnalisar1() {
        int resultado = detector.analisar("Rua Antonio Jos� de S�, 289");
        int resultadoEsperado = 0;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAnalisar2() {
        int resultado = detector.analisar("Rua Antonio J�se de S�, 289");
        int resultadoEsperado = 1;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObterOcorrencias() {
        detector.analisar("Rua Ant�nio J�se de S�, 289");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "J�se[12,15]/S�[20,21]";
        assertEquals(resultadoEsperado, resultado);
    }
}
