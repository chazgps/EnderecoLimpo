package dicionarios;

import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DicionarioTest {

    DicionarioImpl dicionario;

    public class DicionarioImpl extends Dicionario {
    }

    @Before
    public void setUp() {
        dicionario = new DicionarioImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdicionar() {
        dicionario.adicionar(null,null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testProcurarComArgumentoInvalido() {
        dicionario.procurar(null);
    }

    @Test
    public void testProcurarArgumentoValido() {
        dicionario.adicionar("Trv","travessa");

        String resultado = dicionario.procurar("trv");
        String resultadoEsperado = "travessa";
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testObtemEntradas() {
        DicionarioImpl dicionarioTeste = new DicionarioImpl();
        dicionarioTeste.adicionar("Bc","Beco");
        Set conjunto = dicionarioTeste.obtemEntradas();

        Map.Entry entradaExemplo = new Map.Entry() {

            public Object getKey() {
                return "bc";
            }

            public Object getValue() {
                return "beco";
            }

            public Object setValue(Object value) {
                throw new UnsupportedOperationException("Não implementado.");
            }
        };
        boolean resultado = conjunto.contains(entradaExemplo);
        boolean resultadoEsperado = true;

        assertEquals(resultadoEsperado, resultado);
    }
}
