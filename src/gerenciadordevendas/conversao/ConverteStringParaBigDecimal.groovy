package gerenciadordevendas.conversao;

import java.math.BigDecimal;

/**
 *
 * @author ramon
 */
public class ConverteStringParaBigDecimal implements Conversao<String, BigDecimal> {

    @Override
    public BigDecimal converte(String valorNumerico) throws ConversaoException {
        try {
            if (valorNumerico == null){
                throw new ConversaoException("");
            }
            if (valorNumerico.trim().isEmpty()){
                return BigDecimal.ZERO
            }
            return new BigDecimal(valorNumerico)
        } catch (NumberFormatException e) {
            throw new ConversaoException("Não foi possivel converter [${valorNumerico}] para o formato válido");
        }
    }

}
