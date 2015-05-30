package formatadores;

/**
 * Este formatador padroniza um endere�o para que todas as letras estejam em
 * caixa alta (mai�scula).<br>
 * Exemplo: 'Av Antonio Siqueira Neto, 212' ser� formatado para 'AV ANTONIO SIQUEIRA NETO, 212'.
 */
public class FmtMaiuscula implements IFormatador {

    public String formatar(final String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }
        return endereco.toUpperCase();
    }
}
