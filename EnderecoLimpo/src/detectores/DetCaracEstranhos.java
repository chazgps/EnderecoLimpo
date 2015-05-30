package detectores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar caracteres estranhos em um endere�o.
 * Caracteres estranhos s�o todos aqueles que n�o aceit�veis de estarem presentes em um endere�o.
 * Exemplos: �,�,?
 */
public class DetCaracEstranhos implements IDetector {

    private Pattern padrao;
    private StringBuilder buffer;

    public DetCaracEstranhos() {
        buffer = new StringBuilder();

        // Define a mascara
        String mascara = "[^A-Za-z0-9 ,.\\-()#����������������������������������������������]";

        // Compila a expressao
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    public int analisar(String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }

        buffer.setLength(0);

        Matcher matcher = padrao.matcher(endereco);

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
