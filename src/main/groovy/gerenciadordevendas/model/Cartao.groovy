/*
 * GerenciadorDeVendas: Cartao1.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 15:52:09
 */

package gerenciadordevendas.model

import gerenciadordevendas.controller.CodigoBarrasIText;
import gerenciadordevendas.controller.GeradorCodigoBarras;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Ramon Porto
 */
@Entity
class Cartao extends BaseEntity {

    private static final GeradorCodigoBarras GERADOR = new CodigoBarrasIText()

    final String codigoBarras;
    @ManyToOne
    @JoinColumn(name = "cliente")
    final Cliente cliente;

    protected Cartao() {    }

    Cartao(Cliente cliente) {
        this.cliente = Objects.requireNonNull(cliente)
        codigoBarras = GERADOR.gerarCodigoValido()
    }

    void imprimir() {
        GERADOR.imprimirCartao(this)
    }
}

