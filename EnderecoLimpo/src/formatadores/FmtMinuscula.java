package formatadores;

/**
 * Este formatador padroniza um endereço para que todas as letras estejam em
 * caixa baixa (minúscula).
 */
public class FmtMinuscula implements IFormatador {
    public String formatar(final String endereco){
        if (endereco==null){
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }
        
        return endereco.toLowerCase();
    }
}

