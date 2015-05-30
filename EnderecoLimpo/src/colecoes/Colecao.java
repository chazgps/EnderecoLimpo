package colecoes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Contém implementação básica do comportamento que toda coleção de palavras deve ter.<br>
 * A coleção pode vir a ser usada tanto por classes do pacote detectores como formatadores
 * para a apoiá-las na realização de suas tarefas.
 */
public abstract class Colecao {

    private HashSet items;

    public Colecao() {
        items = new HashSet();
    }

    /**
     * Adiciona uma palavra a coleção
     * @param item Palavra a ser adicionada
     */
    public void adicionar(final String item) {
        if (item == null) {
            throw new IllegalArgumentException("Valor nulo não é um item válido para ser adicionado");
        }

        items.add(item.toLowerCase());
    }

    /**
     * Verifica se uma determinada palavra existe na coleção
     * @param item Palavra a ser pesquisada
     * @return Retorna TRUE se a palavra está na coleção
     */
    public boolean existe(final String item) {
        if (item == null) {
            throw new IllegalArgumentException("Valor nulo não é um item válido para ser pesquisado");
        }

        return items.contains(item.toLowerCase());
    }

    /**
     * Retorna uma coleção que é somente leitura dos items nela contidos.
     * @return Um objeto Set contendo os items da coleção
     */
    public Set obtemItems() {
        return Collections.unmodifiableSet(items);
    }
}
