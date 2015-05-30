package detectores;

/**
 * Esta interface � implementada pelas classes que possuem tratamentos
 * especificos para a detec��o de problemas nos enderecos.<br>
 * Para cada problema espec�fico, uma classe do pacote <b>detectores</b> implementa
 * esta interface.<br>
 * Novos detectores que n�o os presentes nesta biblioteca s� precisam implementar
 * esta interface para serem aceitos em um contexto de execu��o de detectores,
 * via a classe DectectorContext.
  */
public interface IDetector {
    /**
     * Realiza a an�lise de um endere�o em busca de problemas
     * @param endereco Endere�o a ser analisado
     * @return Retorna 0 (zero) se o detector que implementa esta interface n�o encontrou problema no endere�o, ou >0 se encontrou.
     */
    int analisar(String endereco);

    /**
     * Obt�m os termos onde foi encontrado problemas no endere�o.<br>
     * Termos s�o partes do endere�o.<br>
     * Segue o  formato de retorno deste m�todo:<br>
     * termo[inicio,fim]/proximo-termo[....<br>
     * Exemplo:<br>
     *      M�aria[5,10]
     * @return Retorna os termos com problema ou NULL se a an�lise do endere�o revelou que nenhum problema foi encontrado.
     */
    String obterOcorrencias();
}