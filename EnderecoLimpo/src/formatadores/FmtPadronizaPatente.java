package formatadores;

import dicionarios.NomesPatentes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Este formatador transforma patentes abreviadas em seus correspondentes expandidos
 * nos endere�os.<br>
 * Patentes s�o t�tulos atribu�dos a pessoas, como por exemplo,
 * Coronel, General, Almirante, Doutor.
 */
public class FmtPadronizaPatente implements IFormatador {

    private NomesPatentes nomesPatentes;
    private Pattern padrao;

    public FmtPadronizaPatente() {
        nomesPatentes = new NomesPatentes();

        inicializa();
    }

    public FmtPadronizaPatente(final NomesPatentes nomes) {
        nomesPatentes = nomes;

        inicializa();
    }

    private void inicializa() {
        // Define a mascara obter cada termo do endere�o, inclusive acentua��o, menos os n�meros e s�mbolos
        // Opcionalmente a palavra pode ser terminada com um sinal de ponto (.)
        // Isto � feito para garantir que tanto "Dr" como "Dr." que s�o casos v�lidos de aparecerem
        // em endere�os, possam ser capturados por este regex.
        String mascara = "\\b\\p{L}+\\b[.]?";

        // Compila a express�o
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    public String formatar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }

        // Efetuando a pesquisa pelo padrao
        Matcher matcher = padrao.matcher(endereco);

        while (matcher.find()) {
            String nome = matcher.group();

            // Vamos garantir que o sinal de ponto n�o existe antes de procurar
            // este termo no dicion�rio.
            String nomePesquisa = nome.replace(".", "");

            // Pesquise no dicion�rio
            String nomeExpandido = nomesPatentes.procurar(nomePesquisa);

            // Encontrou ?
            if (nomeExpandido == null) {
                continue;
            }

            String ladoEsquerdo = endereco.substring(0, matcher.start());

            // O lado direito ap�s onde a patente foi encontrada, n�o inicia
            // com um espa�o ?
            String ladoDireito = endereco.substring(matcher.end());
            
            if (ladoDireito.charAt(0)!=' '){
                // Vamos adicionar um espa�o para que os nomes n�o fiquem
                // colados um no outro
                nomeExpandido=nomeExpandido+" ";
            }
            
            endereco = ladoEsquerdo+nomeExpandido+ladoDireito;
        }

        return endereco;
    }
}
