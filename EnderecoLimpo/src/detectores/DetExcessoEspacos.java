package detectores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar excesso de espa�os em um endere�o.<br>
 * Este excesso pode estar no in�cio, fim ou qualquer outra parte do endere�o.
 */
public class DetExcessoEspacos implements IDetector {

    private Pattern padrao;
    private StringBuilder buffer;

    public DetExcessoEspacos() {
        buffer = new StringBuilder();

        // Define a mascara
        String mascara = "\\s{2,}";

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
