/*
 * GerenciadorDeVendas: Produto.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 16:49:23
 */

package gerenciadordevendas.model

import javax.persistence.Column
import javax.persistence.Temporal
import javax.persistence.TemporalType

@javax.persistence.Entity
class Produto extends BaseEntity implements Comparable<Produto> {

    String nome
    String observacoes

    @Override
    String toString() { nome }

    @Override
    int compareTo(Produto o) { nome <=> o.nome }
}


