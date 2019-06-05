/*
 * GerenciadorDeVendas: ItemVendaTableModel.groovy
 * Enconding: UTF-8
 * Data de criação: 21/09/2018 11:19:57
 */

package gerenciadordevendas.tablemodel

import gerenciadordevendas.JPA
import gerenciadordevendas.model.Cliente
import gerenciadordevendas.model.Conta
import gerenciadordevendas.model.ItemEstoque
import gerenciadordevendas.model.ItemVenda
import gerenciadordevendas.model.Vendedor
import gerenciadordevendas.model.RegistroDeFluxo
import gerenciadordevendas.model.Venda
import java.text.DecimalFormat
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.TypedQuery
import javax.swing.JTable
import javax.swing.ListSelectionModel

class ItemVendaTableModel extends TableModelPesquisavel {

    enum Colunas {
        ITEM("Item"),
        CODIGO_BARRAS("Código Barras"),
        DESCRICAO("Descrição"),
        QNT("QNT"),
        VALOR_UN("Valor UN"),
        SUBTOTAL("SubTotal");

        private String nome

        private Colunas(String nome) {
            this.nome = nome
        }
    }
    
    Cliente padrao
    final DecimalFormat df = new DecimalFormat('R$ #,###.00')
    Venda venda
    Vendedor vendedor
    List<RegistroDeFluxo> transacoes = new ArrayList<>()

    ItemVendaTableModel() {
        super(Colunas.values()*.nome)

        venda = new Venda(null)
    }
    
    ItemVendaTableModel(Venda venda){
        super(Colunas.values()*.nome)
        this.venda = venda;
        transacoes.clear();
        transacoes += venda.listaProdutos;
    }
    
    void setVenda(Venda venda) {
        this.venda = venda;
        transacoes.clear();
        transacoes += venda.listaProdutos;
    }

    void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor
        venda.vendedor = vendedor
    }

    ItemVenda add(ItemVenda iv) {
        venda.addItem(iv)
        transacoes.add(iv)
        fireTableRowsInserted(rowCount - 1, rowCount - 1)
        return iv
    }

    ItemVenda add(ItemEstoque e, int qnt) {
        ItemVenda itemVenda = ItemVenda.Build(e, qnt)
        return add(itemVenda)
    }

    ItemVenda add(String codigoBarras) throws NoResultException {
        ItemEstoque e = buscarProduto(codigoBarras)
        return add(e, 1)
    }

    void remove(int row) {
        RegistroDeFluxo item = transacoes.get(row)
        if (item instanceof ItemVenda) {
            venda.removeItem((ItemVenda) item)
        }
        transacoes.remove(row);
        this.fireTableRowsDeleted(row, row)
    }


    BigDecimal getTotal() {
        return venda.subTotal
    }

    void setConta(Conta c) {
        venda.conta = c
    }

    void finalizarVenda() {
              
        venda = new Venda(null)
        venda.vendedor = vendedor
        transacoes.clear();
        fireTableDataChanged();
    }

    @Override
    void pesquisar(String campo) {
        throw new UnsupportedOperationException("Not supported yet.")
    }


    @Override
    Class<?> getColumnClass(int columnIndex) {
        return Object.class
    }

    @Override
    ItemVenda getObjetoLinha(int linha) {
        venda.listaProdutos.get(linha)
    }

    @Override
    int getRowCount() {
        transacoes.size()
    }

    @Override
    Object getValueAt(int linha, int coluna) {
        RegistroDeFluxo item = transacoes.get(linha)
        if (item instanceof ItemVenda) {
            ItemVenda iv = venda.listaProdutos.get(linha)
            switch (Colunas.values()[coluna]) {
                case Colunas.ITEM:      return linha + 1
                case Colunas.CODIGO_BARRAS:    return iv.itemEstoque.codigoBarras
                case Colunas.DESCRICAO: return iv.itemEstoque.produto.nome
                case Colunas.QNT:       return iv.quantidade
                case Colunas.VALOR_UN:  return df.format(iv.itemEstoque.valorAprazo)
                case Colunas.SUBTOTAL:  return df.format(iv.saldoVenda)
            }
        } else {
            switch (Colunas.values()[coluna]) {
                case Colunas.ITEM:      return linha + 1
                case Colunas.DESCRICAO: return item.tipo
                case Colunas.SUBTOTAL:  return df.format(item.total * -1g)
            }
        }
        return null
    }

    private ItemEstoque buscarProduto(String codigoBarras) throws NoResultException {
        EntityManager em = JPA.getEM()
        TypedQuery<ItemEstoque> tq = em.createQuery("SELECT e FROM ItemEstoque e WHERE e.codBarras = :codBarras", 
                ItemEstoque.class)
        tq.setParameter("codBarras", codigoBarras)

        ItemEstoque result = tq.getSingleResult()

        return result

    }

    @Override
    void atualizaEspacamentoColunas() {
        super.atualizaEspacamentoColunas()
    }

    @Override
    void setJTable(JTable tabela) {
        super.setJTableColumnsWidth(tabela, 5, 12, 56, 5, 10, 10)
        tabela.showGrid = false
        tabela.selectionMode = ListSelectionModel.SINGLE_INTERVAL_SELECTION
    }
}


