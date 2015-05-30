package detectores;

import colecoes.Digrafos;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detector para encontrar letras repetidas em um endereço.<br>
 * Algumas letras repetidas são válidas de estarem presentes em um termo de um endereço, como
 * por exemplo SS em caSSIA ou RR ou coRRimao.<br>
 * Fora estes casos, não existe outro dupla de consoantes iguais que são válidas.<br>
 * Nem todos os casos que esta classe detectar podem ser considerados como válidos, ou seja,
 * são letras repetidas que denotam problema no endereço.<br>
 * Existem nomes de origem estrangeira onde 2 consoantes iguais podem ser totalmente válidas,
 * por exemplo, LL em Giarelli, ou TT em Romanetti, ambos italianos.
 */
public class DetLetrasRepetidas implements IDetector {

    private Pattern padrao;
    private StringBuilder buffer;

    public DetLetrasRepetidas() {
        buffer = new StringBuilder();

        Digrafos digrafos = new Digrafos();
        ArrayList colDigrafos = new ArrayList(digrafos.obtemItems());

        Iterator i = colDigrafos.iterator();

        while (i.hasNext()) {
            String digrafo = (String) i.next();

            buffer.append("^");
            buffer.append(digrafo);
            buffer.append("|");
        }

        // Define a mascara
        String mascara = "([" + buffer.toString() + ".])\\1+";

        // Compila a expressao
        padrao = Pattern.compile(mascara, Pattern.CASE_INSENSITIVE);
    }

    /**
     *
     * @param endereco String a ser classificada
     * @return Retorna 0 (zero) se o endereço não possui alguma letra repetida mais de 1 vez ou 1 se tem.
     */
    public int analisar(String endereco) {
        if (endereco==null){
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }
        
        buffer.setLength(0);

        Matcher matcher = padrao.matcher(endereco);

        boolean encontrou = matcher.find();

        if (!encontrou) {
            return 0;
        }

        do {
            if (buffer.length() > 0) {
                buffer.append(('/'));
            }
            buffer.append(matcher.group());
            buffer.append('[');
            buffer.append(matcher.start());
            buffer.append(',');
            buffer.append(matcher.end() - 1);
            buffer.append(']');
        } while (matcher.find());

        return 1;
    }

    public String obterOcorrencias() {
        if (buffer.length() == 0) {
            return null;
        }

        return buffer.toString();
    }
}
