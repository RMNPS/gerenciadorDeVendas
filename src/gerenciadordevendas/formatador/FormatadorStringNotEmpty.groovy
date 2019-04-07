/*
 * GerenciadorDeVendas: FormatadorStringNotEmpty.java
 * Enconding: UTF-8
 * Data de criação: 28/02/2018 15:32:18
 */
package gerenciadordevendas.formatador;

import gerenciadordevendas.exception.FormatacaoException;

public class FormatadorStringNotEmpty extends Formatador<String, String> {

    @Override
    public String formatar(String nomeCampo, String objeto) throws FormatacaoException {
        if (objeto == null || objeto.trim().isEmpty()) {
            throw new FormatacaoException("Preencha o campo " + nomeCampo);
        }
        return objeto;
    }
}
