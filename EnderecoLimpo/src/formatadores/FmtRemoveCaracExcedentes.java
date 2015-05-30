package formatadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Este formatador remove caracteres excedentes em um endere�o,
 * como execesso de espa�os, por exemplo.
 */
public class FmtRemoveCaracExcedentes implements IFormatador {

    private Pattern padraoZeros;
    private Pattern padraoEspacos;

    public FmtRemoveCaracExcedentes() {
        // Define a mascara para detectar mais de um zero em in�cios de palavras
        String mascaraZeros = "\\b(0)\\1";

        // Define a mascara para detectar mais de um espa�o consecutivo
        String mascaraEspacos = "(\\s)\\1+";

        // Compila as express�es
        padraoZeros = Pattern.compile(mascaraZeros, Pattern.CASE_INSENSITIVE);
        padraoEspacos = Pattern.compile(mascaraEspacos, Pattern.CASE_INSENSITIVE);
    }

    public String formatar(String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }
        
        // Efetuando a pesquisa pelo padrao e remo��o dos zeros
        Matcher matcher = padraoZeros.matcher(endereco);
        endereco=matcher.replaceAll("");

        // Efetuando a pesquisa pelo padrao e trocando por apenas 1 espa�o
        matcher = padraoEspacos.matcher(endereco);
        endereco=matcher.replaceAll(" ");
        endereco=endereco.trim();

        return endereco;
    }
}
