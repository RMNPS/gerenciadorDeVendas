/*
 * GerenciadorDeVendas: FormatadorValidade.java
 * Enconding: UTF-8
 * Data de criação: 08/02/2018 10:05:33
 */
package gerenciadordevendas.formatador;

import gerenciadordevendas.exception.FormatacaoException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramon Porto
 */
class FormatadorValidade extends Formatador<Date, Date>{
    
    Date dataAnterior;

    FormatadorValidade(Date dataAnterior) {
        this.dataAnterior = dataAnterior;
    }
    
    @Override
    Date formatar(String nomeCampo, Date validade) throws FormatacaoException {
        if (validade == null)
            throw new FormatacaoException(nomeCampo + " não pode ser NULL");
        
        if (validade.getTime() != dataAnterior.getTime() && validade.getTime()
                < new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1).getTime().getTime()) {
            throw new FormatacaoException("Você informou uma data de vencimento anterior a ontem");
        }
        return validade;
    }
    
}
