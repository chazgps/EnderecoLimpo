package formatadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Este formatador remove caracteres excedentes em um endereço,
 * como execesso de espaços, por exemplo.
 */
public class FmtRemoveCaracExcedentes implements IFormatador {

    private Pattern padraoZeros;
    private Pattern padraoEspacos;

    public FmtRemoveCaracExcedentes() {
        // Define a mascara para detectar mais de um zero em inícios de palavras
        String mascaraZeros = "\\b(0)\\1";

        // Define a mascara para detectar mais de um espaço consecutivo
        String mascaraEspacos = "(\\s)\\1+";

        // Compila as expressões
        padraoZeros = Pattern.compile(mascaraZeros, Pattern.CASE_INSENSITIVE);
        padraoEspacos = Pattern.compile(mascaraEspacos, Pattern.CASE_INSENSITIVE);
    }

    public String formatar(String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }
        
        // Efetuando a pesquisa pelo padrao e remoção dos zeros
        Matcher matcher = padraoZeros.matcher(endereco);
        endereco=matcher.replaceAll("");

        // Efetuando a pesquisa pelo padrao e trocando por apenas 1 espaço
        matcher = padraoEspacos.matcher(endereco);
        endereco=matcher.replaceAll(" ");
        endereco=endereco.trim();

        return endereco;
    }
}
