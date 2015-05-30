package formatadores;

import java.util.Vector;

/**
 * Esta classe é um contexto de execução de formatadores, possibilitando que
 * vários formatadores sejam executandos juntos para cada endereço que for
 * fornecido neste contexto.
 */
public class FormatadorContext {

    private Vector formatadores;

    public FormatadorContext() {
        formatadores = new Vector();
    }

    public String formatar(String endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }

        for (int i = formatadores.size() - 1; i >= 0; i--) {
            endereco = ((IFormatador) formatadores.elementAt(i)).formatar(endereco);
        }
        return endereco;
    }

    public void adicionaFormatador(IFormatador formatador) {
        if (formatador == null) {
            throw new IllegalArgumentException("Uma referência válida de IFormatador deve ser fornecida");
        }
        formatadores.add(formatador);
    }

    public void removeFormatador(IFormatador formatador) {
        if (formatador == null) {
            throw new IllegalArgumentException("Uma referência válida de IFormatador deve ser fornecida");
        }
        formatadores.remove(formatador);
    }
}
