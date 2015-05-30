package detectores;

import colecoes.NomesImproprios;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar nomes impr�prios em um endere�o.<br>
 * Ele se utiliza de uma cole��o de palavras pr�-conhecidas de baixo cal�o para
 * determinar se um endere�o cont�m algum termo que est� nesta cole��o.
 */
public class DetNomesImproprios implements IDetector {

    private ArrayList colNomesImproprios;
    private StringBuilder buffer;
    private NomesImproprios nomesImproprios;

    public DetNomesImproprios() {
        inicializa(new NomesImproprios());
    }

    public DetNomesImproprios(NomesImproprios nomes) {
        inicializa(nomes);
    }

    private void inicializa(NomesImproprios nomes) {
        nomesImproprios = nomes;
        colNomesImproprios = new ArrayList(nomesImproprios.obtemItems());
        buffer = new StringBuilder();
    }

    /**
     * 
     * @param endereco String a ser classificada
     * @return Retorna 0 (zero) se o endere�o n�o possui algum nome impr�prio ou 1 se tem.
     */
    public int analisar(String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }
        
        for (int i = colNomesImproprios.size() - 1; i > -1; i--) {
            // Monte a express�o regular para encontrar os nomes impr�prios
            // somente em palavras inteiras, n�o em in�cios ou fins de palavras.
            // Desta forma se evita alarmes falsos.
            String nomeImproprio = "\\b" + ((String) colNomesImproprios.get(i)) + "\\b";

            Pattern pattern = Pattern.compile(nomeImproprio, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(endereco);

            // N�o encontramos o nome impr�prio no logradouro ?
            if (!matcher.find()) {
                buffer.setLength(0);
                continue;
            }

            // O buffer ir� conter o nome impr�prio e as posi��es onde ele foi encontrado
            buffer.append(matcher.group());

            do {
                buffer.append('[');
                buffer.append(matcher.start());
                buffer.append(',');
                buffer.append(matcher.end() - 1);
                buffer.append(']');
            } while (matcher.find());

            return 1;
        }

        // Percorremos todas os nomes impr�prios e nenhum coincidiu.
        // Retorne o status de que o nome est� limpo, n�o existe nome impr�prio nele.
        return 0;
    }

    public String obterOcorrencias() {
        if (buffer.length() == 0) {
            return null;
        }

        return buffer.toString();
    }
}
