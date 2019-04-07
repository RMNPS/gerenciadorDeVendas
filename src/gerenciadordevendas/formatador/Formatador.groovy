package gerenciadordevendas.formatador;

import gerenciadordevendas.exception.FormatacaoException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Ramon Porto
 * @param <T1> Tipo de entrada
 * @param <T2> Tipo de saída
 */
abstract class Formatador<T1, T2> {
    
    static final FormatadorDouble DOUBLE_FORMAT = new FormatadorDouble();
    static final FormatadorPreco PRECO_FORMAT = new FormatadorPreco();
    static final FormatadorStringNotEmpty STRING_FORMAT = new FormatadorStringNotEmpty();
    
    abstract T2 formatar(String nomeCampo, T1 objeto) throws FormatacaoException;
    
    static Double formatDouble(String nomeCampo, String objeto){
        return DOUBLE_FORMAT.formatar(nomeCampo, objeto);
    }
    
    static BigDecimal formatPreco(String nomeCampo, String objeto) throws FormatacaoException {
        return PRECO_FORMAT.formatar(nomeCampo, objeto);
    }
    
    static Date formatValidade(String nomeCampo, Date dataAnterior, Date validade) throws FormatacaoException {
        if (validade == null)
            throw new FormatacaoException(nomeCampo + " não pode ser NULL");
        
        if (validade.getTime() != dataAnterior.getTime() && validade.getTime()
                < new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1).getTime().getTime()) {
            throw new FormatacaoException("Você informou uma data de vencimento anterior a ontem");
        }
        return validade;
    }
    
    static <T> T notNull(String nomeCampo, T objeto) throws FormatacaoException {
        if (objeto == null){
            throw new FormatacaoException("Preencha o campo "+ nomeCampo);
        }
        return objeto;
    }
    
    static String stringNotEmpty(String nomeCampo, String campo) throws FormatacaoException {
        return STRING_FORMAT.formatar(nomeCampo, campo);
    }
}
