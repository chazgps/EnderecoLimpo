package formatadores;

import dicionarios.NomesAcentuados;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Este formatador adiciona acentuação a uma palavra, baseando-se em um dicionário
 * de nomes previamente conhecidos como acentuados.<br>
 * Ele também remove acentuação incorreta que esteja no nome e por fim é capaz
 * de manter a capitalização de uma palavra após a acentuação.<br>
 * Por exemplo: Se no endereço surgir a palavra 'mArio', após este formatador receber
 * este endereço, esta palavra será corrigida para 'mÁrio'.
 */
public class FmtAdicionaAcentuacao implements IFormatador {

    private NomesAcentuados nomesAcentuados;
    private String acentos[][] = {{"àáäâã", "a"}, {"ÂÃÁÀÄ", "A"}, {"èéëê", "e"}, {"ÉÈÊË", "E"}, {"ìíïî", "i"}, {"ÍÌÎÏ", "I"}, {"òóöôõ", "o"}, {"ÓÒÔÕÖ", "O"}, {"ùúüû", "u"}, {"ÚÙÛÜ", "U"}, {"ç", "c"}, {"Ç", "C"}};
    private Pattern padrao;
    private StringBuffer buffer;

    public FmtAdicionaAcentuacao() {
        nomesAcentuados = new NomesAcentuados();

        inicializa();
    }

    public FmtAdicionaAcentuacao(final NomesAcentuados nomes) {
        nomesAcentuados = nomes;

        inicializa();
    }

    private void inicializa() {
        // Define a mascara obter cada termo do endereço, inclusive acentuação, menos os números e símbolos
        String mascara = "\\b\\p{L}+\\b";

        // Compila a expressão
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);

        buffer = new StringBuffer();
    }

    public String formatar(String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }
        
        // Efetuando a pesquisa pelo padrao e remoção dos caracteres estranhos
        Matcher matcher = padrao.matcher(endereco);

        while (matcher.find()) {
            String nome = matcher.group();

            // Vamos converter o nome para minuscula e removendo a acentuação
            String nomePesquisa = converteParaPesquisa(nome);

            // Agora com o formato correto, pesquise no dicionário
            String nomeCorreto = nomesAcentuados.procurar(nomePesquisa);

            // Encontrou ?
            if (nomeCorreto != null) {
                nome = acentuaNome(nome, nomeCorreto);
            }

            endereco = endereco.substring(0, matcher.start())+nome+ endereco.substring(matcher.end());
        }
        return endereco;
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
            buffer.append(c);
        }
        nomeConversao = buffer.toString();

        // Descarte o conteúdo do buffer liberando memória
        buffer.setLength(0);

        return nomeConversao;
    }

    // Baseado no nome certo, acentue o errado, mantendo a capitalização de cada letra
    // como originalmente se encontra no nome.
    private String acentuaNome(String nomeErrado, String nomeCerto) {
        int j = nomeCerto.length();

        // Percorra todo o nome certo
        for (int i = 0; i < j; i++) {

            // Um caracter por vez
            char letraNoNomeCerto = nomeCerto.charAt(i);

            char letraNoNomeErrado = nomeErrado.charAt(i);

            if (isAcentuada(letraNoNomeCerto)) {

                // Está em maíscula acentuada ou não acentuada no nome errado ?
                if ((letraNoNomeErrado > 64 & letraNoNomeErrado < 91) ||
                        (letraNoNomeErrado > 191 & letraNoNomeErrado < 221)) {

                    // Transforme em minúsculo
                    //letraNoNomeCerto = String.valueOf(letraNoNomeCerto).toLowerCase().charAt(0);
                    String novaLetra=String.valueOf(letraNoNomeCerto);
                    novaLetra=novaLetra.toUpperCase();
                    letraNoNomeCerto = novaLetra.charAt(0);
                }

            } else {
                // Não é acentuada no nome certo...

                // ...mas no nome errado está acentuada ?
                if (isAcentuada(letraNoNomeErrado)) {
                    // Temos um problema para corrigir, trocando ela por uma não acentuada
                    letraNoNomeCerto = removeAcento(letraNoNomeErrado);
                } else {
                    // Copie ela do jeito que está no nome errado
                    letraNoNomeCerto = nomeErrado.charAt(i);
                }
            }

            buffer.append(letraNoNomeCerto);
        }

        // Temos agora o nome corretamente acentuado, mantendo a capitalização
        // de cada letra original.
        nomeCerto = buffer.toString();

        // Descarte o conteúdo do buffer liberando memória
        buffer.setLength(0);

        return nomeCerto;
    }

    private boolean isAcentuada(final char c) {
        final int pos = "àáäâãÂÃÁÀÄèéëêÉÈÊËìíïîÍÌÎÏòóöôõÓÒÔÕÖùúüûÚÙÛÜçÇ".indexOf(c);

        boolean temAcentuacao = pos > -1;

        return temAcentuacao;
    }

    private char removeAcento(char c) {
        for (int i = acentos.length - 1; i > -1; i--) {
            if (acentos[i][0].indexOf(c) > -1) {
                c = acentos[i][1].charAt(0);
                break;
            }
        }

        return c;
    }
}
