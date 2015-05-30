package detectores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetNomesImpropriosTest {

    IDetector detector;

    @Before
    public void setUp() {
        detector = new DetNomesImproprios();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaoAceitaArgumentoInvalido() {
        detector.analisar(null);
    }

    @Test
    public void testAnalisar1() {
        int resultado = detector.analisar("Rua dos jasmins 258-viado");
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
        detector.analisar("Rua dos jasmins 258-viado");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "viado[20,24]";
        assertEquals(resultadoEsperado, resultado);
    }
}
