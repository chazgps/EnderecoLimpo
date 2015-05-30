package detectores;

import colecoes.NomesImproprios;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar nomes impróprios em um endereço.<br>
 * Ele se utiliza de uma coleção de palavras pré-conhecidas de baixo calão para
 * determinar se um endereço contém algum termo que está nesta coleção.
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
     * @return Retorna 0 (zero) se o endereço não possui algum nome impróprio ou 1 se tem.
     */
    public int analisar(String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }
        
        for (int i = colNomesImproprios.size() - 1; i > -1; i--) {
            // Monte a expressão regular para encontrar os nomes impróprios
            // somente em palavras inteiras, não em inícios ou fins de palavras.
            // Desta forma se evita alarmes falsos.
            String nomeImproprio = "\\b" + ((String) colNomesImproprios.get(i)) + "\\b";

            Pattern pattern = Pattern.compile(nomeImproprio, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(endereco);

            // Não encontramos o nome impróprio no logradouro ?
            if (!matcher.find()) {
                buffer.setLength(0);
                continue;
            }

            // O buffer irá conter o nome impróprio e as posições onde ele foi encontrado
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

        // Percorremos todas os nomes impróprios e nenhum coincidiu.
        // Retorne o status de que o nome está limpo, não existe nome impróprio nele.
        return 0;
    }

    public String obterOcorrencias() {
        if (buffer.length() == 0) {
            return null;
        }

        return buffer.toString();
    }
}
