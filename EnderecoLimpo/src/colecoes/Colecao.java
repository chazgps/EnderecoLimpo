package colecoes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Cont�m implementa��o b�sica do comportamento que toda cole��o de palavras deve ter.<br>
 * A cole��o pode vir a ser usada tanto por classes do pacote detectores como formatadores
 * para a apoi�-las na realiza��o de suas tarefas.
 */
public abstract class Colecao {

    private HashSet items;

    public Colecao() {
        items = new HashSet();
    }

    /**
     * Adiciona uma palavra a cole��o
     * @param item Palavra a ser adicionada
     */
    public void adicionar(final String item) {
        if (item == null) {
            throw new IllegalArgumentException("Valor nulo n�o � um item v�lido para ser adicionado");
        }

        items.add(item.toLowerCase());
    }

    /**
     * Verifica se uma determinada palavra existe na cole��o
     * @param item Palavra a ser pesquisada
     * @return Retorna TRUE se a palavra est� na cole��o
     */
    public boolean existe(final String item) {
        if (item == null) {
            throw new IllegalArgumentException("Valor nulo n�o � um item v�lido para ser pesquisado");
        }

        return items.contains(item.toLowerCase());
    }

    /**
     * Retorna uma cole��o que � somente leitura dos items nela contidos.
     * @return Um objeto Set contendo os items da cole��o
     */
    public Set obtemItems() {
        return Collections.unmodifiableSet(items);
    }
}
