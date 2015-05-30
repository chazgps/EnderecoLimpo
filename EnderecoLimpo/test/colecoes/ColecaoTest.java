package colecoes;

import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ColecaoTest {

    ColecaoImpl colecao;

    public class ColecaoImpl extends Colecao {
    }

    @Before
    public void setUp() {
        colecao = new ColecaoImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdiciona() {
        colecao.adicionar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExisteComArgumentoInvalido() {
        colecao.existe(null);
    }

    @Test
    public void testExisteComArgumentoValido() {
        colecao.adicionar("gomes");

        boolean resultado = colecao.existe("gomes");
        boolean resultadoEsperado = true;
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testGetItems() {
        ColecaoImpl colecaoTeste = new ColecaoImpl();
        colecaoTeste.adicionar("maria");
        Set conjunto = colecaoTeste.obtemItems();

        boolean resultado = conjunto.contains("maria");
        boolean resultadoEsperado = true;
        assertEquals(resultadoEsperado, resultado);
    }
}
