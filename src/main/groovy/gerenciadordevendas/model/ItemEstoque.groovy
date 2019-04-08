/*
 * GerenciadorDeVendas: ItemEstoque1.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 15:45:14
 */

package gerenciadordevendas.model

import groovy.transform.ToString
import java.math.BigDecimal
import java.util.Calendar
import java.util.Date
import javax.persistence.*

@ToString
@Entity
class ItemEstoque extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "produto")
    Produto produto
    double quantidade
    String codigoBarras
    @ManyToOne
    @JoinColumn(name = "fornecedor")
    Fornecedor fornecedor
    @Temporal(TemporalType.DATE)
    Date validade
    @Column(precision = 12, scale = 2)
    BigDecimal valorCusto
    @Column(precision = 12, scale = 2)
    BigDecimal valorAprazo
    @Column(precision = 12, scale = 2)
    BigDecimal valorAvista
    int numeroParcelas
    @Column(precision = 12, scale = 2)
    BigDecimal valorParcelaSugerida
    String caminhoFoto
    @ManyToOne
    @JoinColumn(name = "cor")
    Cor cor
    @JoinColumn(name = "tamanho")
    Tamanho tamanho
    @Transient
    boolean imprimirEmBranco

    ItemEstoque() {
        super()
        valorCusto = 0g
        valorAprazo = 0g
        valorAvista = 0g
        valorParcelaSugerida = 0g
        validade = Calendar.instance.time
    }
    
    static ItemEstoque newItemEmBranco() {
        ItemEstoque ie = new ItemEstoque();
        ie.imprimirEmBranco = true;
        return ie;
    }

}


