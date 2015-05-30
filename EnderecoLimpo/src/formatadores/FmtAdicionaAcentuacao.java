package formatadores;

import dicionarios.NomesAcentuados;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Este formatador adiciona acentua��o a uma palavra, baseando-se em um dicion�rio
 * de nomes previamente conhecidos como acentuados.<br>
 * Ele tamb�m remove acentua��o incorreta que esteja no nome e por fim � capaz
 * de manter a capitaliza��o de uma palavra ap�s a acentua��o.<br>
 * Por exemplo: Se no endere�o surgir a palavra 'mArio', ap�s este formatador receber
 * este endere�o, esta palavra ser� corrigida para 'm�rio'.
 */
public class FmtAdicionaAcentuacao implements IFormatador {

    private NomesAcentuados nomesAcentuados;
    private String acentos[][] = {{"�����", "a"}, {"�����", "A"}, {"����", "e"}, {"����", "E"}, {"����", "i"}, {"����", "I"}, {"�����", "o"}, {"�����", "O"}, {"����", "u"}, {"����", "U"}, {"�", "c"}, {"�", "C"}};
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
        // Define a mascara obter cada termo do endere�o, inclusive acentua��o, menos os n�meros e s�mbolos
        String mascara = "\\b\\p{L}+\\b";

        // Compila a express�o
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);

        buffer = new StringBuffer();
    }

    public String formatar(String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }
        
        // Efetuando a pesquisa pelo padrao e remo��o dos caracteres estranhos
        Matcher matcher = padrao.matcher(endereco);

        while (matcher.find()) {
            String nome = matcher.group();

            // Vamos converter o nome para minuscula e removendo a acentua��o
            String nomePesquisa = converteParaPesquisa(nome);

            // Agora com o formato correto, pesquise no dicion�rio
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
            buffer.append(c);
        }
        nomeConversao = buffer.toString();

        // Descarte o conte�do do buffer liberando mem�ria
        buffer.setLength(0);

        return nomeConversao;
    }

    // Baseado no nome certo, acentue o errado, mantendo a capitaliza��o de cada letra
    // como originalmente se encontra no nome.
    private String acentuaNome(String nomeErrado, String nomeCerto) {
        int j = nomeCerto.length();

        // Percorra todo o nome certo
        for (int i = 0; i < j; i++) {

            // Um caracter por vez
            char letraNoNomeCerto = nomeCerto.charAt(i);

            char letraNoNomeErrado = nomeErrado.charAt(i);

            if (isAcentuada(letraNoNomeCerto)) {

                // Est� em ma�scula acentuada ou n�o acentuada no nome errado ?
                if ((letraNoNomeErrado > 64 & letraNoNomeErrado < 91) ||
                        (letraNoNomeErrado > 191 & letraNoNomeErrado < 221)) {

                    // Transforme em min�sculo
                    //letraNoNomeCerto = String.valueOf(letraNoNomeCerto).toLowerCase().charAt(0);
                    String novaLetra=String.valueOf(letraNoNomeCerto);
                    novaLetra=novaLetra.toUpperCase();
                    letraNoNomeCerto = novaLetra.charAt(0);
                }

            } else {
                // N�o � acentuada no nome certo...

                // ...mas no nome errado est� acentuada ?
                if (isAcentuada(letraNoNomeErrado)) {
                    // Temos um problema para corrigir, trocando ela por uma n�o acentuada
                    letraNoNomeCerto = removeAcento(letraNoNomeErrado);
                } else {
                    // Copie ela do jeito que est� no nome errado
                    letraNoNomeCerto = nomeErrado.charAt(i);
                }
            }

            buffer.append(letraNoNomeCerto);
        }

        // Temos agora o nome corretamente acentuado, mantendo a capitaliza��o
        // de cada letra original.
        nomeCerto = buffer.toString();

        // Descarte o conte�do do buffer liberando mem�ria
        buffer.setLength(0);

        return nomeCerto;
    }

    private boolean isAcentuada(final char c) {
        final int pos = "����������������������������������������������".indexOf(c);

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
