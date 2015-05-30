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
        int resultado = detector.analisar("Rua Antonio José de Sá, 289");
        int resultadoEsperado = 0;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAnalisar2() {
        int resultado = detector.analisar("Rua Antonio Jóse de Sá, 289");
        int resultadoEsperado = 1;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObterOcorrencias() {
        detector.analisar("Rua Antônio Jóse de Sà, 289");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "Jóse[12,15]/Sà[20,21]";
        assertEquals(resultadoEsperado, resultado);
    }
}
