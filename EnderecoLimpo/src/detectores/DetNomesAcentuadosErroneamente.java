package detectores;

import dicionarios.NomesAcentuados;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar nomes acentuados erroneamente.<br>
 * Este detector se utiliza de um dicion�rio de nomes conhecidos que contenham acentua��o
 * para determinar se o que est� em algum dos termos do endere�os est� com esta
 * acetua��o errada ou n�o.
 */
public class DetNomesAcentuadosErroneamente implements IDetector {

    private Pattern padrao;
    private StringBuilder buffer, bufferConversao;
    private NomesAcentuados nomesAcentuados;
    private String acentos[][] = {{"�����", "a"}, {"�����", "A"}, {"����", "e"}, {"����", "E"}, {"����", "i"}, {"����", "I"}, {"�����", "o"}, {"�����", "O"}, {"����", "u"}, {"����", "U"}, {"�", "c"}, {"�", "C"}};

    public DetNomesAcentuadosErroneamente() {
        inicializa(new NomesAcentuados());
    }

    public DetNomesAcentuadosErroneamente(NomesAcentuados nomes) {
        inicializa(nomes);
    }

    private void inicializa(NomesAcentuados nomes) {
        nomesAcentuados = nomes;
        buffer = new StringBuilder();
        bufferConversao = new StringBuilder();

        // Define a mascara para separar palavras com caracteres normais e
        // caracteres do bloco Unicode Latin1 incluindo letras acentuadas
        String mascara = "[\\pL&&\\p{L1}]+";

        // Compila a express�o
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    /**
     * 
     * @param endereco String a ser classificada
     * @return Retorna 0 (zero) se o endere�o n�o possui algum nome acentuado de forma errada ou 1 se tem.
     */
    public int analisar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }

        buffer.setLength(0);

        Matcher matcher = padrao.matcher(endereco);

        while (matcher.find()) {

            String nome = matcher.group();

            // Vamos converter o nome para minuscula e removendo a acentua��o
            String nomePesquisa = converteParaPesquisa(nome);

            // Agora com o formato correto, pesquise no dicion�rio
            String nomeCorreto = nomesAcentuados.procurar(nomePesquisa);

            if (nomeCorreto == null) {
                continue;
            }

            if (nomeCorreto.equals(nome.toLowerCase())) {
                continue;
            }

            if (buffer.length() > 0) {
                buffer.append(('/'));
            }

            buffer.append(nome);
            buffer.append('[');
            buffer.append(matcher.start());
            buffer.append(',');
            buffer.append(matcher.end() - 1);
            buffer.append(']');
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

    private String converteParaPesquisa(String nomeConversao) {
        int j = nomeConversao.length();

        for (int i = 0; i < j; i++) {
            char c = nomeConversao.charAt(i);

            for (int k = 0; k < acentos.length; k++) {
                // Encontrou a letra em um dos conjuntos acentuados ?
                if (acentos[k][0].indexOf(c) > -1) {
                    // Ent�o trata-se de uma vogal acentuada. Substitua pelo
                    // equivalente sem acentua��o
                    c = acentos[k][1].charAt(0);
                } else {
                    // Trata-se de uma letra n�o acentuada
                    // Est� em mai�scula ?
                    if (c > 64 & c < 91) {
                        // Converte para minuscula
                        c += 32;
                    }
                }
            }
            bufferConversao.append(c);
        }
        nomeConversao = bufferConversao.toString();

        // Descarte o conte�do do buffer liberando mem�ria
        bufferConversao.setLength(0);

        return nomeConversao;
    }
}
