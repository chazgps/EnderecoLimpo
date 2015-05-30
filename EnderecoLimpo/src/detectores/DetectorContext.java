package detectores;

import java.util.Vector;

/**
 * Esta classe permite que v�rios detectores sejam agrupados e ajude na totaliza��o
 * das ocorr�ncias dos casos detectados por cada um.
 */
public class DetectorContext {

    private Vector detectores;
    private long[] resultados;

    public DetectorContext() {
        detectores = new Vector();
    }

    /**
     * Prepara o contexto para uma nova execu��o, tendo o efeito de zerar as 
     * estat�sticas recolhidas at� o momento.
     */
    public void preparaNovaMedicao() {
        resultados = new long[detectores.size() + 1];
    }

    /**
     * Analisa o endere�o, atualizando as estat�sticas no contexto para os casos detectados
     * @param endereco Endere�o a ser analisado por cada detector que esta ativo no contexto de execu��o.
     */
    public void analisar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }

        resultados[0]++;

        for (int i = detectores.size() - 1; i >= 0; i--) {
            detectores.IDetector detector = (IDetector) detectores.elementAt(i);

            if (detector.analisar(endereco) > 0) {
                int indice = i + 1;
                resultados[indice] = resultados[indice] + 1;
            }
        }
    }

    /**
     * Obtem a quantidade de casos detectados por um determinado Dectector.
     * @param detector Detector cuja as estat�sticas desejamos recolher
     * @return Quantidade de casos detectados
     */
    public long obtemResultado(IDetector detector) {
        if (detector == null) {
            if (resultados == null) {
                return 0;
            } else {
                return resultados[0];
            }
        }

        for (int i = detectores.size() - 1; i >= 0; i--) {
            if (detectores.elementAt(i).equals(detector)) {
                return resultados[i + 1];
            }
        }

        return 0;
    }

    /**
     * Adiciona um detector ao contexto de execu��o
     * @param detector Um detector a ser adicionado no processo de execu��o do contexto.
     */
    public void adicionaDetector(IDetector detector) {
        if (detector == null) {
            throw new IllegalArgumentException("Uma refer�ncia v�lida de IDetector deve ser fornecida");
        }

        detectores.add(detector);
    }

    /**
     * Remove um detector do contexto de execu��o
     * @param detector Detector que deve ser removido para n�o mais fazer parte do contexto de execu��o.
     */
    public void removeDetector(IDetector detector) {
        if (detector == null) {
            throw new IllegalArgumentException("Uma refer�ncia v�lida de IDetector deve ser fornecida");
        }

        detectores.remove(detector);
    }
}
