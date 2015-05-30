package formatadores;

/**
 * Este formatador padroniza um endereço para que todas as letras estejam em
 * caixa alta (maiúscula).<br>
 * Exemplo: 'Av Antonio Siqueira Neto, 212' será formatado para 'AV ANTONIO SIQUEIRA NETO, 212'.
 */
public class FmtMaiuscula implements IFormatador {

    public String formatar(final String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }
        return endereco.toUpperCase();
    }
}
