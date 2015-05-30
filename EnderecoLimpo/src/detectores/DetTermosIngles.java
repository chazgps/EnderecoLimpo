package detectores;

import dicionarios.TermosIngles;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar termos em ingl�s em um endere�o.<br>
 * Em um cadastro, por vezes pode aparecer endere�os com algum dos termos em ingl�s que
 * muito provavelmente se refere a cita��es deste sendo usados fora do pa�s.<br>
 * Um exemplo desta situa��o � de uma empresa americana que tem em sua base de dados
 * o cadastro de algu�m aqui no Brasil e decidi postar uma carta-resposta.<br>
 * Este endere�o no cadastro dela vai estar no formato americano mas a postagem ser� aqui
 * para o Brasil e a empresa pode acabar n�o fazendo nenhum tratamento neste endere�o.<br>
 * Para os Correios do Brasil, isto pode significar problema pois dependendo do formato
 * que estiver o endere�o e os termos empregados em ingl�s, os Correios podem ter dificuldade
 * para efetuarem a entrega da correspond�ncia, por exemplo.<br>
 * Outras atividades que usam o endere�o tamb�m ser�o prejudicadas com estes endere�os
 * que contenham termos em ingl�s.
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

        // Define a mascara obter cada termo do endere�o, inclusive acentua��o, menos os n�meros e s�mbolos
        String mascara = "\\b\\p{L}+\\b";

        // Compila a express�o
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    /**
     * 
     * @param endereco String a ser classificada
     * @return Retorna 0 (zero) se o endere�o n�o possui algum termo em ingl�s ou 1 se tem.
     */
    public int analisar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }

        buffer.setLength(0);

        // Efetuando a pesquisa pelo padrao e remo��o dos caracteres estranhos
        Matcher matcher = padrao.matcher(endereco);

        while (matcher.find()) {
            String nome = matcher.group();

            // Pesquise no dicion�rio
            String nomeEmPortugues = termosIngles.procurar(nome);

            // Encontrou ?
            if (nomeEmPortugues == null) {
                continue;
            }

            // O buffer ir� conter os termos em ingl�s e as posi��es onde eles foram encontrados
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
