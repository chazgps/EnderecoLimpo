package dicionarios;

/**
 * Contém a coleção de Tipos de Logradouros válidos de aparecerem em um endereço
 */
public class LogradourosTipos extends Dicionario {

    public LogradourosTipos() {
        this.adicionar("R", "Rua");
        this.adicionar("Av", "Avenida");
        this.adicionar("Al", "Alameda");
    }
}
