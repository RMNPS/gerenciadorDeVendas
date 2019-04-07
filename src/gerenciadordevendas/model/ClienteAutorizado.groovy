/*
 * GerenciadorDeVendas: ClienteAutorizado1.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 16:03:52
 */

package gerenciadordevendas.model

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Cliente que comprar em nome de outro cliente
 * @author ramon
 */
@Entity
class ClienteAutorizado extends BaseEntity implements Comparable<ClienteAutorizado>, Autorizado {

    String nome;
    @ManyToOne
    @JoinColumn(name = "cliente")
    Cliente cliente;

    @Override
    public int compareTo(ClienteAutorizado o) {
        return nome.compareTo(o.nome);
    }

}

