/*
 * GerenciadorDeVendas: Venda.groovy
 * Enconding: UTF-8
 * Data de criação: 18/09/2018 18:00:03
 */

package gerenciadordevendas.model

import gerenciadordevendas.exception.TransacaoException;
import gerenciadordevendas.JPA;
import gerenciadordevendas.Regras;
import groovy.transform.ToString
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Representa a compra de um cliente
 *
 * @author ramon
 */
@Entity
@ToString
class Venda extends RegistroDeFluxo {

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])    
    @JoinColumn(name = "conta")
    Conta conta;
    @OneToMany(mappedBy = "venda")
    List<ItemVenda> listaProdutos
    @Column(precision = 12, scale = 2)
    BigDecimal subTotal
    @Column(precision = 12, scale = 2)
    BigDecimal total
    Estado estado
    @ManyToOne
    @JoinColumn(name="vendedor")
    Vendedor vendedor
    @OneToMany(mappedBy = "venda")
    List<Parcela> parcelas
    String observacoes

    protected Venda() {  }
    
    Venda(Conta conta, Vendedor vendedor) {
        this.listaProdutos = new ArrayList<>()

        this.subTotal = 0
        this.estado = Estado.ABERTA;
        this.conta = Objects.requireNonNull(conta)
        this.vendedor = Objects.requireNonNull(vendedor)
    }

    List<ItemVenda> getListaProdutos() {
        return Collections.unmodifiableList(listaProdutos);
    }

    BigDecimal setPorcentagemDesconto(BigDecimal d) throws TransacaoException {
        if (isAlteravel()) {
            if (d < 0g)
                throw new TransacaoException("O desconto não pode ser negativo")
            
            if (d > BigDecimal.valueOf(Regras.LIMITE_PORCENTAGEM_DESCONTO)) 
                throw new TransacaoException("O desconto não pode ser maior que " + Regras.LIMITE_PORCENTAGEM_DESCONTO + "%")
            d = d.divide(new BigDecimal("100"), new MathContext(2)).multiply(subTotal)
            total = subTotal - d
            return d
        }
        return 0g
    }
    
    
    BigDecimal setDescontoReal(BigDecimal d) throws TransacaoException {
        if (isAlteravel()) {
            if (d < 0g)
                throw new TransacaoException("O desconto não pode ser negativo")
            
            if (d > BigDecimal.valueOf(Regras.LIMITE_DINHEIRO)) 
                throw new TransacaoException("O desconto não pode ser maior que " + Regras.LIMITE_DINHEIRO)
            
            total = subTotal - d;
            return (d / subTotal) * 100g;
        }
        return 0
    }

    private boolean isAlteravel() {
        if (estado != Estado.PAGA && estado != Estado.EM_CONTA) {
            return true
        }
        throw new TransacaoException("A venda já foi finalizada.");
    }

    def addItem(ItemVenda iv) {
        if (isAlteravel()) {
            listaProdutos.add(iv);
            total = subTotal = subTotal + iv.saldoVenda;
            iv.setVenda(this);
        }
    }

    def removeItem(ItemVenda iv) {
        if (isAlteravel()) {
            listaProdutos.remove(iv);
            total = subTotal = subTotal - iv.saldoVenda;
        }
    }

    def removeItem(int index) {
        ItemVenda item = listaProdutos.get(index);
        removeItem(item);
    }

    @Override
    BigDecimal getTotal() {
        return total;
    }

    def finalizarVenda() {
        if (isAlteravel()) {

            conta.addVenda(this);

            //ID da conta padrão
            estado = conta.id == Conta.padrao().id 
                ? Estado.PAGA 
                : Estado.EM_CONTA
            
            EntityManager em = JPA.getEM();
            em.transaction.begin();
            removeItensVendidosDoEstoque(em);
            em.transaction.commit();
            em.close();
        }
    }

    private void removeItensVendidosDoEstoque(EntityManager em) {

        for (ItemVenda iv : listaProdutos) {
            ItemEstoque ie = iv.itemEstoque;
            double qntVendida = iv.quantidade;

            if (qntVendida > 0) {
                ie.quantidade -= qntVendida;
            }
            em.merge(ie);
        }
    }
    

    @Override
    String toString() { "Venda{" + "subTotal=" + subTotal + ", total=" + total + '}' }

    @Override
    String getTipo() { "Venda" }

}


