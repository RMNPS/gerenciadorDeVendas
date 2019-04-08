/*
 * GerenciadorDeVendas: ContaAcimaDoLimite.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 16:37:04
 */

package gerenciadordevendas.model

/**
 *
 * @author Ramon Porto
 */
import java.util.Optional;

class ContaAcimaDoLimite implements AvaliacaoConta {

    @Override
    Optional<String> avaliar(Conta c) {
        if (c.limite?.compareTo(c.saldo) < 0) {
            return Optional.of('A conta ultrapassou o limite de R$ ' + c.getLimite());
        }
        return Optional.empty();
    }

}
