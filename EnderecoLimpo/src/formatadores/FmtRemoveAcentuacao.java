package formatadores;

/**
 * Este formatador remove a acentua��o que por ventura exista em um endere�o.<br>
 * Endere�os que n�o tem acentua��o permanecem inalterados.
 */
public class FmtRemoveAcentuacao implements IFormatador {

    private String acentos[][] = {{"�����", "a"}, {"�����", "A"}, {"����", "e"}, {"����", "E"}, {"����", "i"}, {"����", "I"}, {"�����", "o"}, {"�����", "O"}, {"����", "u"}, {"����", "U"}, {"�", "c"}, {"�", "C"}};

    public String formatar(final String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endere�o n�o pode ser nulo");
        }
        
        int j = endereco.length();
        char[] output = new char[j];

        for (int i = j - 1; i >= 0; i--) {
            char c = endereco.charAt(i);

            for (int k = acentos.length - 1; k >= 0; k--) {
                String conjunto = acentos[k][0];

                if (conjunto.indexOf(c) > -1) {
                    c = acentos[k][1].charAt(0);
                }
            }

            output[i] = c;
        }

        return new String(output);
    }
}
