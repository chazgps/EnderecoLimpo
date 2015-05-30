package detectores;

/**
 * Esta interface é implementada pelas classes que possuem tratamentos
 * especificos para a detecção de problemas nos enderecos.<br>
 * Para cada problema específico, uma classe do pacote <b>detectores</b> implementa
 * esta interface.<br>
 * Novos detectores que não os presentes nesta biblioteca só precisam implementar
 * esta interface para serem aceitos em um contexto de execução de detectores,
 * via a classe DectectorContext.
  */
public interface IDetector {
    /**
     * Realiza a análise de um endereço em busca de problemas
     * @param endereco Endereço a ser analisado
     * @return Retorna 0 (zero) se o detector que implementa esta interface não encontrou problema no endereço, ou >0 se encontrou.
     */
    int analisar(String endereco);

    /**
     * Obtém os termos onde foi encontrado problemas no endereço.<br>
     * Termos são partes do endereço.<br>
     * Segue o  formato de retorno deste método:<br>
     * termo[inicio,fim]/proximo-termo[....<br>
     * Exemplo:<br>
     *      Mºaria[5,10]
     * @return Retorna os termos com problema ou NULL se a análise do endereço revelou que nenhum problema foi encontrado.
     */
    String obterOcorrencias();
}