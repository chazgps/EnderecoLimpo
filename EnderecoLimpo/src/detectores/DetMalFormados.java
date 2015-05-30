package detectores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar endere�os mal-formados.<br>
 * Endere�os mal-formados s�o aqueles que n�o tem uma estrutura m�nima que representa um endere�o v�lido.<br>
 * Um endere�o m�nimo � formado por <b><tipo do logradouro><logradouro><numera��o></b><br>
 * Exemplo de um endere�o v�lido: R dos Alecrins 200<br>
 * Exemplo de um endere�o mal-formado: RDOVOGA
 */
public class DetMalFormados implements IDetector {

    private Pattern padrao;
    private StringBuilder buffer;

    public DetMalFormados() {
        buffer = new StringBuilder();

        // Define a mascara
        String mascara = "[^\\s]+";

        // Compila a expressao
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    public int analisar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }

        buffer.setLength(0);

        Matcher matcher = padrao.matcher(endereco);

        boolean encontrou = matcher.find();

        if (!encontrou) {
            return 0;
        }

        int grupos = 0;

        do {
            buffer.append(matcher.group());
            buffer.append('[');
            buffer.append(matcher.start());
            buffer.append(',');
            buffer.append(matcher.end() - 1);
            buffer.append(']');

            grupos++;
        } while (matcher.find());

        if (grupos == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    public String obterOcorrencias() {
        if (buffer.length() == 0) {
            return null;
        }

        return buffer.toString();
    }
}
