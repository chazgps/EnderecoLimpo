package formatadores;

/**
 * Esta interface � implementada pelas classes que possuem tratamentos
 * especificos para a corre��o de problemas nos enderecos.<br>
 * Para cada corre��o espec�fica, uma classe do pacote <b>formatadores</b> implementa
 * esta interface.<br>
 * Novos formatadores que n�o os presentes nesta biblioteca s� precisam implementar
 * esta interface para serem aceitos em um contexto de execu��o de formatadores,
 * via a classe @see FormatadorContext.
 */
public interface IFormatador {
    /**
     * Formata um endere�o, adicionando, transformando ou retirando informa��o
     * para que o mesmo se adeque a um determinado prop�sito.
     * @param endereco Endere�o a ser formatado
     * @return Endere�o formatado ou o endere�o original se o formatador n�o detectou que o endere�o precisava de alguma transforma��o.
     */
    public String formatar(final String endereco);
}
