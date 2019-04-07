/*
 * GerenciadorDeVendas: AvaliadorConta2.groovy
 * Enconding: UTF-8
 * Data de criação: 11/09/2018 15:45:06
 */

package gerenciadordevendas.avaliacao

import gerenciadordevendas.model.AvaliacaoConta
import gerenciadordevendas.model.Conta
import gerenciadordevendas.model.ContaAcimaDoLimite
import gerenciadordevendas.model.ContaAlemDoPrazo

/**
 *
 * @author Ramon Porto
 */
class AvaliadorConta {
    
    def Set<String> avaliar(Conta conta) {
        Set mensagens = new HashSet()
        
        def avaliacoes = [new ContaAcimaDoLimite(), new ContaAlemDoPrazo()]
        avaliacoes.each {
            def avaliacao = it.avaliar(conta)
            if (avaliacao.isPresent())
                mensagens << avaliacao.get()
        }
        
        return mensagens
    }
}