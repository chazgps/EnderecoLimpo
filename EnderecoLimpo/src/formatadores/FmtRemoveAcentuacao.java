package formatadores;

/**
 * Este formatador remove a acentuação que por ventura exista em um endereço.<br>
 * Endereços que não tem acentuação permanecem inalterados.
 */
public class FmtRemoveAcentuacao implements IFormatador {

    private String acentos[][] = {{"àáäâã", "a"}, {"ÂÃÁÀÄ", "A"}, {"èéëê", "e"}, {"ÉÈÊË", "E"}, {"ìíïî", "i"}, {"ÍÌÎÏ", "I"}, {"òóöôõ", "o"}, {"ÓÒÔÕÖ", "O"}, {"ùúüû", "u"}, {"ÚÙÛÜ", "U"}, {"ç", "c"}, {"Ç", "C"}};

    public String formatar(final String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endereço não pode ser nulo");
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
