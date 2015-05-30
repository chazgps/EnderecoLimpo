package detectores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar nomes acentuados.<br>
 * Este detector facilita a tarefa de encontrar nomes com acentua��o
 * e a partir destes, aqueles que podem estar com a acentua��o incorreta.
 */
public class DetNomesAcentuados implements IDetector {

    private Pattern padrao;
    private StringBuilder buffer;
    private char[] letrasAcentuadas = {
        '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�',
        '�', '�', '�', '�', '�', '�', '�',
        '�', '�', '�', '�', '�', '�', '�', '�',
        '�', '�', '�', '�', '�', '�', '�', '�', '�', '�',
        '�', '�', '�', '�', '�', '�', '�', '�', '�', '�'
    };

    public DetNomesAcentuados() {
        buffer = new StringBuilder();

        // Define a mascara para separar palavras com caracteres normais e
        // caracteres do bloco Unicode Latin1 incluindo letras acentuadas
        String mascara = "[\\pL&&\\p{L1}]+";

        // Compila a express�o
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    public int analisar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }

        buffer.setLength(0);

        Matcher matcher = padrao.matcher(endereco);

        while (matcher.find()) {

            String nome = matcher.group();

            for (int j = 0; j < letrasAcentuadas.length; j++) {

                if (nome.indexOf(letrasAcentuadas[j]) > -1) {

                    if (buffer.length() > 0) {
                        buffer.append(('/'));
                    }
                    buffer.append(matcher.group());
                    buffer.append('[');
                    buffer.append(matcher.start());
                    buffer.append(',');
                    buffer.append(matcher.end() - 1);
                    buffer.append(']');
                }
            }
        }
        int resultado = (buffer.length() > 0 ? 1 : 0);

        return resultado;
    }

    public String obterOcorrencias() {
        if (buffer.length() == 0) {
            return null;
        }

        return buffer.toString();
    }
}
