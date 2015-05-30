package formatadores;

/**
 * Este formatador padroniza um endere�o para que todas as letras estejam em
 * caixa baixa (min�scula).
 */
public class FmtMinuscula implements IFormatador {
    public String formatar(final String endereco){
        if (endereco==null){
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }
        
        return endereco.toLowerCase();
    }
}

