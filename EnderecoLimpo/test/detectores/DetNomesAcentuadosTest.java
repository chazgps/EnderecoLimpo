package detectores;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetNomesAcentuadosTest {

    IDetector detector;

    @Before
    public void setUp() {
        detector = new DetNomesAcentuados();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaoAceitaArgumentoInvalido() {
        detector.analisar(null);
    }

    @Test
    public void testAnalisar() {
        int resultado = detector.analisar("Rua Antonio Jos� dos Santos, 289");
        int resultadoEsperado = 1;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAnalisar2() {
        int resultado = detector.analisar("Rua Jose Anastacio Dias 129");
        int resultadoEsperado = 0;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObterOcorrencias() {
        detector.analisar("Rua Faf� de Bel�m, 66");
        String resultado = detector.obterOcorrencias();
        String resultadoEsperado = "Faf�[4,7]/Bel�m[12,16]";
        assertEquals(resultadoEsperado, resultado);
    }
}
