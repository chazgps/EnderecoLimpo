package dicionarios;

import java.util.HashMap;
import java.util.Set;

/**
 * Contém implementação básica do comportamento que todo dicionário de palavras deve ter.<br>
 * O dicionário pode vir a ser usado tanto por classes do pacote detectores como formatadores
 * para a apoiá-las na realização de suas tarefas.
 */
public abstract class Dicionario {
    private HashMap items;

    public Dicionario(){
        items=new HashMap();
    }

    /**
     * Adiciona um par de palavras ao dicionário
     * @param de Uma palavra-origem a ser pesquisada no dicionário
     * @param para Uma palavra-destino, que é a equivalencia da palavra-origem procurada
     */
    public void adicionar(final String de, final String para){
        if (de == null || para == null) {
            throw new IllegalArgumentException("Valor nulo não é um valor válido para nenhum dos argumentos");
        }

        items.put(de.toLowerCase(),para.toLowerCase());
    }

    /**
     * Procura uma palavra no dicionário
     * @param de A palavra a ser procurada
     * @return Se existe uma equivalência no dicionário, ela será retornada, senão será retornado NULL
     */
    public String procurar(final String de){
        if (de == null) {
            throw new IllegalArgumentException("Valor nulo não é uma entrada válida para ser pesquisada");
        }

        return (String) items.get(de.toLowerCase());
    }

    /**
     * Obtém as entradas no dicionário
     * @return Um objeto do tipo Set contendo as entradas (palavras-origem) do dicionário
     */
    public Set obtemEntradas(){
        return (Set) items.entrySet();
    }
}
