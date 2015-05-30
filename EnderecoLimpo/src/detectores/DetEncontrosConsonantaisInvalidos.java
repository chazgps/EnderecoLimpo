package detectores;

import colecoes.EncontrosConsonantais;
import dicionarios.TermosAbreviados;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar encontros consonantais inv�lidos.<br>
 * Encontros consonantais s�o 2 ou mais consoantes juntas em uma palavra.<br>
 * Exemplos de encontros v�lidos: XC em eXCesso ou  MBR em PenuMBRa<br>
 * Exemplos de inv�lidos: TC (Teoricamente n�o existe uma palavra v�lida que contenha estas 2 consoantes nesta sequ�ncia.<br>
 * Para evitar falsos positivos, como em 'R DA CONCORDIA TV DO ALEMAO', � usado um
 * dicion�rio de Termos Abreviados para ajudar a ignorar estes encontros consonantais
 * inv�lidos, mas que s�o termos abreviados e por isto n�o devem ser apontados
 * por este detector como casos concretos que indiquem problema.
 */
public class DetEncontrosConsonantaisInvalidos implements IDetector {

    private Pattern padraoIdentificaEncontro;
    private Pattern padraoSeparaTermos;
    private StringBuilder buffer;
    private EncontrosConsonantais enc_con;
    private TermosAbreviados termos;

    public DetEncontrosConsonantaisInvalidos() {
        inicializa(new EncontrosConsonantais(), new TermosAbreviados());
    }

    public DetEncontrosConsonantaisInvalidos(EncontrosConsonantais ec, TermosAbreviados ta) {
        inicializa(ec, ta);
    }

    private void inicializa(EncontrosConsonantais ec, TermosAbreviados ta) {
        enc_con = ec;
        termos = ta;

        buffer = new StringBuilder();

        // Define a mascara que ir� pegar qualquer grupo de 2 ou mais
        // consoantes consecutivas
        String mascara = "[a-z&&[^aeiou]]{2,}";

        // Compila a expressao
        padraoIdentificaEncontro = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);

        // Define a mascara para separar palavras com caracteres normais e
        // caracteres do bloco Unicode Latin1 incluindo letras acentuadas
        mascara = "[\\pL&&\\p{L1}]+";

        // Compila a express�o
        padraoSeparaTermos = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    /**
     *
     * @param endereco String a ser classificada
     * @return Retorna 0 (zero) se o endere�o n�o possui alguma letra repetida mais de 1 vez ou 1 se tem.
     */
    public int analisar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }

        buffer.setLength(0);

        // Aplique o regex para obtermos os termos que formam o endere�o
        Matcher matcher = padraoSeparaTermos.matcher(endereco);

        while (matcher.find()) {

            String termo = matcher.group();

            // Aplique o regex para tentarmos identificar grupos de consoantes
            // no termo extra�do
            Matcher matcherEncontro = padraoIdentificaEncontro.matcher(termo);

            while (matcherEncontro.find()) {

                // Quais s�o o conjunto de consoantes encontrados
                String letras = matcherEncontro.group();

                // Se trata de um encontro consonantal v�lido ?
                boolean encontroValido = enc_con.existe(letras);

                if (encontroValido) {
                    continue;
                }

                // N�o � um v�lido, verifique se o nome onde elas foram encontradas
                // formam uma abrevia��o conhecida
                String semAbreviacao = termos.procurar(termo);

                if (semAbreviacao != null) {
                    continue;
                }

                if (buffer.length() > 0) {
                    buffer.append(('/'));
                }

                int inicio = matcher.start()+matcherEncontro.start();

                buffer.append(letras);
                buffer.append('[');
                buffer.append(inicio);
                buffer.append(',');
                buffer.append(inicio+letras.length()-1);
                buffer.append(']');
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
