package formatadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Este formatador remove de um endere�o caracteres estranhos que n�o s�o
 * considerados como v�lidos de estarem presentes em um endere�o comum.
 */
public class FmtRemoveCaracEstranhos implements IFormatador {

    private Pattern padrao;

    public FmtRemoveCaracEstranhos() {
        // Define a mascara para detectar caracteres estranhos
        String mascara = "[^\\p{L} 0-9(),-]";

        // Compila a express�o
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    public String formatar(String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }
        
        // Efetuando a pesquisa pelo padrao e remo��o dos caracteres estranhos
        Matcher matcher = padrao.matcher(endereco);
        endereco=matcher.replaceAll("");

        return endereco;
    }
}
