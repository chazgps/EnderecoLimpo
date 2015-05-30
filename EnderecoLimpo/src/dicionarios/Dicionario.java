package dicionarios;

import java.util.HashMap;
import java.util.Set;

/**
 * Cont�m implementa��o b�sica do comportamento que todo dicion�rio de palavras deve ter.<br>
 * O dicion�rio pode vir a ser usado tanto por classes do pacote detectores como formatadores
 * para a apoi�-las na realiza��o de suas tarefas.
 */
public abstract class Dicionario {
    private HashMap items;

    public Dicionario(){
        items=new HashMap();
    }

    /**
     * Adiciona um par de palavras ao dicion�rio
     * @param de Uma palavra-origem a ser pesquisada no dicion�rio
     * @param para Uma palavra-destino, que � a equivalencia da palavra-origem procurada
     */
    public void adicionar(final String de, final String para){
        if (de == null || para == null) {
            throw new IllegalArgumentException("Valor nulo n�o � um valor v�lido para nenhum dos argumentos");
        }

        items.put(de.toLowerCase(),para.toLowerCase());
    }

    /**
     * Procura uma palavra no dicion�rio
     * @param de A palavra a ser procurada
     * @return Se existe uma equival�ncia no dicion�rio, ela ser� retornada, sen�o ser� retornado NULL
     */
    public String procurar(final String de){
        if (de == null) {
            throw new IllegalArgumentException("Valor nulo n�o � uma entrada v�lida para ser pesquisada");
        }

        return (String) items.get(de.toLowerCase());
    }

    /**
     * Obt�m as entradas no dicion�rio
     * @return Um objeto do tipo Set contendo as entradas (palavras-origem) do dicion�rio
     */
    public Set obtemEntradas(){
        return (Set) items.entrySet();
    }
}
