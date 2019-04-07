package gerenciadordevendas.formatador;

import gerenciadordevendas.exception.FormatacaoException;
import java.util.Arrays;

/**
 *
 * @author Ramon Porto
 */
class FormatadorTelefone extends Formatador<String, String> {

    int[] codigosDDD = [ //Estes são os códigos DDD permitidos no Brasil
        11, 12, 13, 14, 15, 16, 17, 18, 19,
        21, 22, 24, 27, 28, 31, 32, 33, 34,
        35, 37, 38, 41, 42, 43, 44, 45, 46,
        47, 48, 49, 51, 53, 54, 55, 61, 62,
        64, 63, 65, 66, 67, 68, 69, 71, 73,
        74, 75, 77, 79, 81, 82, 83, 84, 85,
        86, 87, 88, 89, 91, 92, 93, 94, 95,
        96, 97, 98, 99];

    @Override
    public String formatar(String nomeCampo, String tel) throws FormatacaoException {
        if (tel == null) {
            throw new FormatacaoException(nomeCampo + " não pode ser NULL");
        }
        String numTelefone = tel.replaceAll("\\D", "");

        if (numTelefone.isEmpty()) {
            throw new FormatacaoException(nomeCampo + " está vazio");
        }
        if (Arrays.binarySearch(codigosDDD, Integer.parseInt(numTelefone.substring(0, 2))) == -1) {
            throw new FormatacaoException("DDD inválido. Os DDDs possíveis são:\n" + Arrays.toString(codigosDDD));
        }
        if (numTelefone.length() < 10) {
            throw new FormatacaoException(nomeCampo + ": Número muito pequeno (Mínimo 10)");
        } else if (numTelefone.length() > 11) {
            throw new FormatacaoException(nomeCampo + ": Número muito grande (Máximo 11)");
        }
        numTelefone = numTelefone.replaceFirst("(\\d{2})(\\d{4})(\\d+)", '($1)$2-$3');

        return numTelefone;
    }

}
