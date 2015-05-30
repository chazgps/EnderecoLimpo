package formatadores;

import dicionarios.NomesPatentes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Este formatador transforma patentes abreviadas em seus correspondentes expandidos
 * nos endereços.<br>
 * Patentes são títulos atribuídos a pessoas, como por exemplo,
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
        // Define a mascara obter cada termo do endereço, inclusive acentuação, menos os números e símbolos
        // Opcionalmente a palavra pode ser terminada com um sinal de ponto (.)
        // Isto é feito para garantir que tanto "Dr" como "Dr." que são casos válidos de aparecerem
        // em endereços, possam ser capturados por este regex.
        String mascara = "\\b\\p{L}+\\b[.]?";

        // Compila a expressão
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    public String formatar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }

        // Efetuando a pesquisa pelo padrao
        Matcher matcher = padrao.matcher(endereco);

        while (matcher.find()) {
            String nome = matcher.group();

            // Vamos garantir que o sinal de ponto não existe antes de procurar
            // este termo no dicionário.
            String nomePesquisa = nome.replace(".", "");

            // Pesquise no dicionário
            String nomeExpandido = nomesPatentes.procurar(nomePesquisa);

            // Encontrou ?
            if (nomeExpandido == null) {
                continue;
            }

            String ladoEsquerdo = endereco.substring(0, matcher.start());

            // O lado direito após onde a patente foi encontrada, não inicia
            // com um espaço ?
            String ladoDireito = endereco.substring(matcher.end());
            
            if (ladoDireito.charAt(0)!=' '){
                // Vamos adicionar um espaço para que os nomes não fiquem
                // colados um no outro
                nomeExpandido=nomeExpandido+" ";
            }
            
            endereco = ladoEsquerdo+nomeExpandido+ladoDireito;
        }

        return endereco;
    }
}
