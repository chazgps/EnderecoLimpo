package detectores;

import dicionarios.NomesAcentuados;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar nomes acentuados erroneamente.<br>
 * Este detector se utiliza de um dicionário de nomes conhecidos que contenham acentuação
 * para determinar se o que está em algum dos termos do endereços está com esta
 * acetuação errada ou não.
 */
public class DetNomesAcentuadosErroneamente implements IDetector {

    private Pattern padrao;
    private StringBuilder buffer, bufferConversao;
    private NomesAcentuados nomesAcentuados;
    private String acentos[][] = {{"àáäâã", "a"}, {"ÂÃÁÀÄ", "A"}, {"èéëê", "e"}, {"ÉÈÊË", "E"}, {"ìíïî", "i"}, {"ÍÌÎÏ", "I"}, {"òóöôõ", "o"}, {"ÓÒÔÕÖ", "O"}, {"ùúüû", "u"}, {"ÚÙÛÜ", "U"}, {"ç", "c"}, {"Ç", "C"}};

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

        // Compila a expressão
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    /**
     * 
     * @param endereco String a ser classificada
     * @return Retorna 0 (zero) se o endereço não possui algum nome acentuado de forma errada ou 1 se tem.
     */
    public int analisar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }

        buffer.setLength(0);

        Matcher matcher = padrao.matcher(endereco);

        while (matcher.find()) {

            String nome = matcher.group();

            // Vamos converter o nome para minuscula e removendo a acentuação
            String nomePesquisa = converteParaPesquisa(nome);

            // Agora com o formato correto, pesquise no dicionário
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
                    // Então trata-se de uma vogal acentuada. Substitua pelo
                    // equivalente sem acentuação
                    c = acentos[k][1].charAt(0);
                } else {
                    // Trata-se de uma letra não acentuada
                    // Está em maiúscula ?
                    if (c > 64 & c < 91) {
                        // Converte para minuscula
                        c += 32;
                    }
                }
            }
            bufferConversao.append(c);
        }
        nomeConversao = bufferConversao.toString();

        // Descarte o conteúdo do buffer liberando memória
        bufferConversao.setLength(0);

        return nomeConversao;
    }
}
