package detectores;

import dicionarios.TermosIngles;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar termos em inglês em um endereço.<br>
 * Em um cadastro, por vezes pode aparecer endereços com algum dos termos em inglês que
 * muito provavelmente se refere a citações deste sendo usados fora do país.<br>
 * Um exemplo desta situação é de uma empresa americana que tem em sua base de dados
 * o cadastro de alguém aqui no Brasil e decidi postar uma carta-resposta.<br>
 * Este endereço no cadastro dela vai estar no formato americano mas a postagem será aqui
 * para o Brasil e a empresa pode acabar não fazendo nenhum tratamento neste endereço.<br>
 * Para os Correios do Brasil, isto pode significar problema pois dependendo do formato
 * que estiver o endereço e os termos empregados em inglês, os Correios podem ter dificuldade
 * para efetuarem a entrega da correspondência, por exemplo.<br>
 * Outras atividades que usam o endereço também serão prejudicadas com estes endereços
 * que contenham termos em inglês.
 */
public class DetTermosIngles implements IDetector {

    private StringBuilder buffer;
    private TermosIngles termosIngles;
    private Pattern padrao;

    public DetTermosIngles() {
        inicializa(new TermosIngles());
    }

    public DetTermosIngles(TermosIngles termos) {
        inicializa(termos);
    }

    private void inicializa(TermosIngles termos) {
        termosIngles = termos;
        buffer = new StringBuilder();

        // Define a mascara obter cada termo do endereço, inclusive acentuação, menos os números e símbolos
        String mascara = "\\b\\p{L}+\\b";

        // Compila a expressão
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    /**
     * 
     * @param endereco String a ser classificada
     * @return Retorna 0 (zero) se o endereço não possui algum termo em inglês ou 1 se tem.
     */
    public int analisar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }

        buffer.setLength(0);

        // Efetuando a pesquisa pelo padrao e remoção dos caracteres estranhos
        Matcher matcher = padrao.matcher(endereco);

        while (matcher.find()) {
            String nome = matcher.group();

            // Pesquise no dicionário
            String nomeEmPortugues = termosIngles.procurar(nome);

            // Encontrou ?
            if (nomeEmPortugues == null) {
                continue;
            }

            // O buffer irá conter os termos em inglês e as posições onde eles foram encontrados
            buffer.append(nome);
            buffer.append('[');
            buffer.append(matcher.start());
            buffer.append(',');
            buffer.append(matcher.end() - 1);
            buffer.append(']');
        }

        if (buffer.length()>0){
            return 1;
        } else {
            return 0;
        }
    }

    public String obterOcorrencias() {
        if (buffer.length() == 0) {
            return null;
        }

        return buffer.toString();
    }
}
