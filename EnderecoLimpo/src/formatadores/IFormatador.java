package formatadores;

/**
 * Esta interface é implementada pelas classes que possuem tratamentos
 * especificos para a correção de problemas nos enderecos.<br>
 * Para cada correção específica, uma classe do pacote <b>formatadores</b> implementa
 * esta interface.<br>
 * Novos formatadores que não os presentes nesta biblioteca só precisam implementar
 * esta interface para serem aceitos em um contexto de execução de formatadores,
 * via a classe @see FormatadorContext.
 */
public interface IFormatador {
    /**
     * Formata um endereço, adicionando, transformando ou retirando informação
     * para que o mesmo se adeque a um determinado propósito.
     * @param endereco Endereço a ser formatado
     * @return Endereço formatado ou o endereço original se o formatador não detectou que o endereço precisava de alguma transformação.
     */
    public String formatar(final String endereco);
}
