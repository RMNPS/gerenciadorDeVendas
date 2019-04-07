/*
 * GerenciadorDeVendas: FormatadorPreco.java
 * Enconding: UTF-8
 * Data de criação: 08/02/2018 09:26:09
 */
package gerenciadordevendas.formatador;

import gerenciadordevendas.exception.FormatacaoException;
import java.math.BigDecimal;

/**
 *
 * @author Ramon Porto
 */
class FormatadorPreco extends Formatador<String, BigDecimal> {

    @Override
    public BigDecimal formatar(String nomeCampo, String objeto) throws FormatacaoException {
        if (objeto == null)
            throw new FormatacaoException(nomeCampo + " não pode ser NULL")
        
        if (objeto.isEmpty())
            throw new FormatacaoException(nomeCampo + " não pode ser vazio")
        
        if (!objeto.matches("\\d+.?\\d*"))
            throw new FormatacaoException(nomeCampo + " em formato inválido")
        
        try {
            BigDecimal preco = new BigDecimal(objeto)
            return preco
            
        } catch (NumberFormatException ex){
            throw new FormatacaoException("Ocorreu um erro na conversão de ${nomeCampo}:\n", ex)
        }
    }

}
