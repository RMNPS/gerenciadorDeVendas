/*
 * GerenciadorDeVendas: FormatadorPreco.java
 * Enconding: UTF-8
 * Data de criação: 08/02/2018 09:26:09
 */
package gerenciadordevendas.formatador;

import gerenciadordevendas.exception.FormatacaoException;
import java.math.BigDecimal;
import java.text.DecimalFormat
import java.text.ParseException

/**
 *
 * @author Ramon Porto
 */
class FormatadorPreco extends Formatador<String, BigDecimal> {

    private static DecimalFormat df
    
    private static DecimalFormat inicia() {
        df = new DecimalFormat()
        df.setParseBigDecimal(true)
    }
    
    @Override
    public BigDecimal formatar(String nomeCampo, String objeto) throws FormatacaoException {
        if (df == null) {
            inicia()
        }
        if (objeto == null)
            throw new FormatacaoException(nomeCampo + " não pode ser NULL")
        
        if (objeto.isEmpty())
            throw new FormatacaoException(nomeCampo + " não pode ser vazio")
        
        if (!objeto.matches("\\d+.?\\d*"))
        throw new FormatacaoException(nomeCampo + " em formato inválido")
        
        try {
            return (BigDecimal) df.parse(objeto);
        } catch (NumberFormatException ex){
            throw new FormatacaoException("Ocorreu um erro na conversão de ${nomeCampo}:\n", ex)
        }
        catch (ParseException ex) {
            throw new FormatacaoException("Ocorreu um erro na conversão de ${nomeCampo}:\n", ex)
        }
    }

}
