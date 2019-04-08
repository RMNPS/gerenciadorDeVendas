/*
 * GerenciadorDeVendas: Parcela.groovy
 * Enconding: UTF-8
 * Data de criação: 19/03/2019 10:07:24
 */

package gerenciadordevendas.model
import groovy.transform.EqualsAndHashCode
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Temporal
import javax.persistence.TemporalType

/**
 *
 * @author Ramon Porto
 */
@Entity
@EqualsAndHashCode(callSuper = true)
class Parcela extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "venda")
    Venda venda
    @Column(precision = 12, scale = 3)
    BigDecimal valor;
    @ManyToOne
    @JoinColumn(name = "formaPagamento")
    FormaPagamento formaPagamento
    @Temporal(TemporalType.DATE)
    Date vencimento
    @Temporal(TemporalType.DATE)
    /** Data em que a parcela foi paga */
    Date dataPagamento
}

