/*
 * GerenciadorDeVendas: FormatadorDouble.java
 * Enconding: UTF-8
 * Data de criação: 08/02/2018 09:54:50
 */
package gerenciadordevendas.formatador;

import gerenciadordevendas.exception.FormatacaoException;

/**
 *
 * @author Ramon Porto
 */
class FormatadorDouble extends Formatador<String, Double>{

    @Override
    Double formatar(String nomeCampo, String objeto) throws FormatacaoException {
        if (objeto == null)
            throw new FormatacaoException(nomeCampo + " não pode ser NULL")
        
        if (objeto.isEmpty())
            throw new FormatacaoException(nomeCampo + " não pode ser vazio")
        
        if (!objeto.matches("-?\\d+.?\\d*"))
            throw new FormatacaoException(nomeCampo +  " em formato inválido")
        try {
            Double preco = Double.valueOf(objeto)
            return preco
            
        } catch (NumberFormatException ex){
            throw new FormatacaoException("Ocorreu um erro na conversão de " + nomeCampo + ":\n", ex)
        }
    }
    
}
