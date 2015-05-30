package formatadores;

import java.util.StringTokenizer;
import colecoes.Preposicao;

/**
 * Este formatador capitaliza a primeira letra de cada termo do endereço, ou seja,
 * transforma-a em maiúscula e as demais em minúsculas.<br>
 * Ela leva em conta também as preposições de ligação, e estas são mantidas
 * em minúscula.
 */
public class FmtMaiusculaSomentePrimeira implements IFormatador {
    private Preposicao preposicoes;
    
    public FmtMaiusculaSomentePrimeira(){
        preposicoes = new Preposicao();
    }

    public FmtMaiusculaSomentePrimeira(final Preposicao colecao){
        if (colecao==null){
            throw new IllegalArgumentException("É obrigatório o fornecimento de um objeto Preposicao");
        }
        
        preposicoes=colecao;
    }
    
    public String formatar(final String endereco){
        if (endereco==null){
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }
        
        StringTokenizer st=new StringTokenizer(endereco," ");
        StringBuilder sb=new StringBuilder();
        
        while (st.hasMoreTokens()){
            String palavra = st.nextToken();
            
            // A palavra atual não é uma preposição?
            // Um termo de ligação entre nome e sobrenome ou sobrenomes...
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
