package gerenciadordevendas.model;

import gerenciadordevendas.model.Conta;
import java.util.Optional;

/**
 *
 * @author ramon
 */
public interface AvaliacaoConta {
    Optional<String> avaliar(Conta c);
}
