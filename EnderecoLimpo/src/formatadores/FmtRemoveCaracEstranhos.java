package formatadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Este formatador remove de um endereço caracteres estranhos que não são
 * considerados como válidos de estarem presentes em um endereço comum.
 */
public class FmtRemoveCaracEstranhos implements IFormatador {

    private Pattern padrao;

    public FmtRemoveCaracEstranhos() {
        // Define a mascara para detectar caracteres estranhos
        String mascara = "[^\\p{L} 0-9(),-]";

        // Compila a expressão
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    public String formatar(String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }
        
        // Efetuando a pesquisa pelo padrao e remoção dos caracteres estranhos
        Matcher matcher = padrao.matcher(endereco);
        endereco=matcher.replaceAll("");

        return endereco;
    }
}
