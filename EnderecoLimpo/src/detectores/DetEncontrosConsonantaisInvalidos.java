package detectores;

import colecoes.EncontrosConsonantais;
import dicionarios.TermosAbreviados;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar encontros consonantais inválidos.<br>
 * Encontros consonantais são 2 ou mais consoantes juntas em uma palavra.<br>
 * Exemplos de encontros válidos: XC em eXCesso ou  MBR em PenuMBRa<br>
 * Exemplos de inválidos: TC (Teoricamente não existe uma palavra válida que contenha estas 2 consoantes nesta sequência.<br>
 * Para evitar falsos positivos, como em 'R DA CONCORDIA TV DO ALEMAO', é usado um
 * dicionário de Termos Abreviados para ajudar a ignorar estes encontros consonantais
 * inválidos, mas que são termos abreviados e por isto não devem ser apontados
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

        // Define a mascara que irá pegar qualquer grupo de 2 ou mais
        // consoantes consecutivas
        String mascara = "[a-z&&[^aeiou]]{2,}";

        // Compila a expressao
        padraoIdentificaEncontro = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);

        // Define a mascara para separar palavras com caracteres normais e
        // caracteres do bloco Unicode Latin1 incluindo letras acentuadas
        mascara = "[\\pL&&\\p{L1}]+";

        // Compila a expressão
        padraoSeparaTermos = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    /**
     *
     * @param endereco String a ser classificada
     * @return Retorna 0 (zero) se o endereço não possui alguma letra repetida mais de 1 vez ou 1 se tem.
     */
    public int analisar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }

        buffer.setLength(0);

        // Aplique o regex para obtermos os termos que formam o endereço
        Matcher matcher = padraoSeparaTermos.matcher(endereco);

        while (matcher.find()) {

            String termo = matcher.group();

            // Aplique o regex para tentarmos identificar grupos de consoantes
            // no termo extraído
            Matcher matcherEncontro = padraoIdentificaEncontro.matcher(termo);

            while (matcherEncontro.find()) {

                // Quais são o conjunto de consoantes encontrados
                String letras = matcherEncontro.group();

                // Se trata de um encontro consonantal válido ?
                boolean encontroValido = enc_con.existe(letras);

                if (encontroValido) {
                    continue;
                }

                // Não é um válido, verifique se o nome onde elas foram encontradas
                // formam uma abreviação conhecida
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
