package formatadores;

import java.util.StringTokenizer;
import colecoes.Preposicao;

/**
 * Este formatador capitaliza a primeira letra de cada termo do endere�o, ou seja,
 * transforma-a em mai�scula e as demais em min�sculas.<br>
 * Ela leva em conta tamb�m as preposi��es de liga��o, e estas s�o mantidas
 * em min�scula.
 */
public class FmtMaiusculaSomentePrimeira implements IFormatador {
    private Preposicao preposicoes;
    
    public FmtMaiusculaSomentePrimeira(){
        preposicoes = new Preposicao();
    }

    public FmtMaiusculaSomentePrimeira(final Preposicao colecao){
        if (colecao==null){
            throw new IllegalArgumentException("� obrigat�rio o fornecimento de um objeto Preposicao");
        }
        
        preposicoes=colecao;
    }
    
    public String formatar(final String endereco){
        if (endereco==null){
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }
        
        StringTokenizer st=new StringTokenizer(endereco," ");
        StringBuilder sb=new StringBuilder();
        
        while (st.hasMoreTokens()){
            String palavra = st.nextToken();
            
            // A palavra atual n�o � uma preposi��o?
            // Um termo de liga��o entre nome e sobrenome ou sobrenomes...
            if (preposicoes.existe(palavra)){
                palavra=palavra.toLowerCase();
                
            } else {
                palavra=String.format("%s%s",Character.toUpperCase(palavra.charAt(0)),
                        palavra.substring(1).toLowerCase());
            }
            
            sb.append(palavra);
            
            if (st.hasMoreTokens()){
                sb.append(' ');
            }
        }
        
        return sb.toString();
    }
}
