/*
 * GerenciadorDeVendas: FormatadorNotNull.java
 * Enconding: UTF-8
 * Data de criação: 08/02/2018 10:57:04
 */
package gerenciadordevendas.formatador;

import gerenciadordevendas.exception.FormatacaoException;

/**
 *
 * @author Ramon Porto
 */
public class FormatadorNotNull extends Formatador {

    @Override
    public Object formatar(String nomeCampo, Object objeto) throws FormatacaoException {
        if (objeto == null){
            throw new FormatacaoException("Preencha o campo ${nomeCampo}")
        }
        return objeto
    }

    

   

}
