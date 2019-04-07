package gerenciadordevendas.conversao;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Converte uma String com data em DD/MM/YY para Date de validade
 *
 * @author ramon
 *
 */
class ConverteStringParaValidade implements Conversao<String, Date> {

    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    Date converte(String sValidade) throws ConversaoException {
        try {
            Date validade = format.parse(sValidade);
            return validade;
        } catch (java.text.ParseException ex) {
            throw new ConversaoException("Insira uma data válida no padrão DD/MM/AAAA");
        }
    }

}
