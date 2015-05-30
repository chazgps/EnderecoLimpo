package detectores;

import java.util.Vector;

/**
 * Esta classe permite que vários detectores sejam agrupados e ajude na totalização
 * das ocorrências dos casos detectados por cada um.
 */
public class DetectorContext {

    private Vector detectores;
    private long[] resultados;

    public DetectorContext() {
        detectores = new Vector();
    }

    /**
     * Prepara o contexto para uma nova execução, tendo o efeito de zerar as 
     * estatísticas recolhidas até o momento.
     */
    public void preparaNovaMedicao() {
        resultados = new long[detectores.size() + 1];
    }

    /**
     * Analisa o endereço, atualizando as estatísticas no contexto para os casos detectados
     * @param endereco Endereço a ser analisado por cada detector que esta ativo no contexto de execução.
     */
    public void analisar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço não pode ser nulo");
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
     * @param detector Detector cuja as estatísticas desejamos recolher
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
     * Adiciona um detector ao contexto de execução
     * @param detector Um detector a ser adicionado no processo de execução do contexto.
     */
    public void adicionaDetector(IDetector detector) {
        if (detector == null) {
            throw new IllegalArgumentException("Uma referência válida de IDetector deve ser fornecida");
        }

        detectores.add(detector);
    }

    /**
     * Remove um detector do contexto de execução
     * @param detector Detector que deve ser removido para não mais fazer parte do contexto de execução.
     */
    public void removeDetector(IDetector detector) {
        if (detector == null) {
            throw new IllegalArgumentException("Uma referência válida de IDetector deve ser fornecida");
        }

        detectores.remove(detector);
    }
}
