package detectores;

import dicionarios.Dicionario;
import dicionarios.LogradourosTipos;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar termos repetidos em um endereço.<br>
 * Exemplo: Rua rua dos Alecrins 200
 */
public class DetTermosRepetidos implements IDetector {

    private Pattern padrao;
    private StringBuilder buffer;
    private Dicionario dicTipoLogradouro;

    public DetTermosRepetidos() {
        padrao = Pattern.compile("\\b(\\w+)\\b\\s*\\1\\b", Pattern.CASE_INSENSITIVE);
        buffer = new StringBuilder();

        // Aqui fazemos uso de um dicionário com os tipos de logradouros
        // Ele irá nos ajudar na tarefa de encontrar termos repetidos, já que uma
        // prévia análise revela que a maior parcela de problemas está nesta parte do endereço
        // Outros dicionários no futuro também podem vir a ser usados;
        dicTipoLogradouro = new LogradourosTipos();
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
        buffer.setLength(0);

        // Vamos empregar 2 técnicas para analisar termos repetidos
        // A 1a é baseada em identificar as repetições usando Expressões Regulares
        // A 2a é baseada na pesquisa em um dicionário de Tipos de Logradouro

        // Usando a 1a técnica - RegEx
        int ret1 = classificaPorRegex(endereco);

        // A 1a técnica nada encontrou.
        // Usando a 2a técnica - Pesquisa em dicionário
        int ret2 = classificaPorDicionario(endereco);

        return (ret1 == 1 || ret2 == 1 ? 1 : 0);
    }

    private int classificaPorRegex(String input) {
        Matcher matcher = padrao.matcher(input);

        int ret = classifica(input, matcher);

        return ret;
    }

    private int classificaPorDicionario(String input) {
        Iterator i = dicTipoLogradouro.obtemEntradas().iterator();

        while (i.hasNext()) {
            Map.Entry m = (Map.Entry) i.next();

            String entrada = (String) m.getKey();
            String valor = (String) m.getValue();

            // Monta uma expressão regular capaz de procurar a entrada e o valor do dicionário
            // tanto nesta ordem quanto na ordem inversa
            String expressao = "\\b" + entrada + "\\b\\W\\b" + valor + "\\b";
            expressao += "|\\b" + valor + "\\b\\W\\b" + entrada + "\\b";

            Pattern pattern = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);

            int ret = classifica(input, matcher);
            if (ret == 1) {
                return 1;
            }
        }

        return 0;
    }

    private int classifica(String input, Matcher matcher) {
        boolean encontrou = matcher.find();

        if (!encontrou) {
            return 0;
        }

        do {
            if (buffer.length() > 0) {
                buffer.append(('/'));
            }
            buffer.append(matcher.group());
            buffer.append('[');
            buffer.append(matcher.start());
            buffer.append(',');
            buffer.append(matcher.end() - 1);
            buffer.append(']');
        } while (matcher.find());

        return 1;
    }

    public String obterOcorrencias() {
        if (buffer.length() == 0) {
            return null;
        }

        return buffer.toString();
    }
}
