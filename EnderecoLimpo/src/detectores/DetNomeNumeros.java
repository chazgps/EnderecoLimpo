package detectores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar numera��es que est�o concactenadas a algum termo do endere�o.<br>
 * Este tipo de situa��o � mais comum do que parece e normalmente ocorre quando durante a
 * manipula��o do endere�o durante alguma migra��o dos dados de uma base para outra,
 * quem realiza a opera��o "inventa" de fazer um merge de dois campos separados (logradouro e numera��o)
 * para um �nico campo na tabela destino, sem levar em conta de colocar pelo menos
 * um espa�o entre os termos.<br>
 * Um exemplo de endere�o com este tipo de problema: RUA CAMPOS SALLES200, CENTRO
 */
public class DetNomeNumeros implements IDetector {

    private Pattern padrao;
    private StringBuilder buffer;

    public DetNomeNumeros() {
        buffer = new StringBuilder();

        // Define a mascara
        String mascara = "[A-Za-z����������������������������������������������]+[0-9]+\\w";

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
