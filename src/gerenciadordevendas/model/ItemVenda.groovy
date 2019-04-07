/*
 * GerenciadorDeVendas: ItemVenda1.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 15:30:21
 */

package gerenciadordevendas.model

import gerenciadordevendas.Regras
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import groovy.transform.ToString
 
@ToString
@Entity
class ItemVenda extends RegistroDeFluxo {

    @ManyToOne
    @JoinColumn(name = "ItemEstoque")
    ItemEstoque itemEstoque
    
    @ManyToOne
    @JoinColumn(name = "venda")
    Venda venda
    double quantidade

    static ItemVenda Build(ItemEstoque itemEstoque, int quantidade) {
        validarQuantidade(quantidade)

        ItemVenda i = new ItemVenda()
        i.itemEstoque = itemEstoque
        i.quantidade = quantidade

        return i
    }

    BigDecimal getSaldoVenda() {
        println("valor a prazo = "+ itemEstoque.valorAprazo)
        println("quantidade = "+ quantidade)
        return itemEstoque.valorAprazo * BigDecimal.valueOf(quantidade);
    }
    
    private static void validarQuantidade(double quantidade) {
        if (quantidade < 1)
            throw new RuntimeException("Quantidade negativa não é permitida.")
            
        else if (quantidade > Regras.QUANTIDADE_MAXIMA_PRODUTO) 
            throw new RuntimeException("Quantidade superior ao limite máximo ("
                    + Regras.QUANTIDADE_MAXIMA_PRODUTO + "). Para liberar limite de itens"
                    + "superior ao estabelecido favor verificar configurações de sistema.")
    }

    @Override
    BigDecimal getTotal() { saldoVenda }

}


