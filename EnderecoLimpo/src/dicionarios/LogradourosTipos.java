package dicionarios;

/**
 * Cont�m a cole��o de Tipos de Logradouros v�lidos de aparecerem em um endere�o
 */
public class LogradourosTipos extends Dicionario {

    public LogradourosTipos() {
        this.adicionar("R", "Rua");
        this.adicionar("Av", "Avenida");
        this.adicionar("Al", "Alameda");
    }
}
