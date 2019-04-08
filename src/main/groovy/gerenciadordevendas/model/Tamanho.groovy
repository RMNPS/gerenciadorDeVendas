/*
 * GerenciadorDeVendas: Tamanho.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 16:49:23
 */

package gerenciadordevendas.model

import javax.persistence.Column
import javax.persistence.Temporal
import javax.persistence.TemporalType

@javax.persistence.Entity
class Tamanho extends BaseEntity implements Comparable<Tamanho>, EntidadeSomenteComNome {

    String nome

    @Override
    String toString() { nome }

    @Override
    int compareTo(Tamanho o) { nome <=> o.nome }
}


