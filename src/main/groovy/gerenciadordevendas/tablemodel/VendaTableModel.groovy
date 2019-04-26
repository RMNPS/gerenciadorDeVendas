/*
 * GerenciadorDeVendas: VendaTableModel.groovy
 * Enconding: UTF-8
 * Data de criação: 21/09/2018 11:19:57
 */

package gerenciadordevendas.tablemodel

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.Cliente;
import gerenciadordevendas.model.Conta;
import gerenciadordevendas.model.ItemEstoque;
import gerenciadordevendas.model.ItemVenda;
import gerenciadordevendas.model.Vendedor;
import gerenciadordevendas.model.RegistroDeFluxo;
import gerenciadordevendas.model.Venda;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

class VendaTableModel extends TableModelPesquisavel {

    enum Colunas {
        ITEM("Item"),
        CODIGO_BARRAS("Código Barras"),
        DESCRICAO("Descrição"),
        QNT("QNT"),
        VALOR_UN("Valor UN"),
        SUBTOTAL("SubTotal");

        private String nome;

        private Colunas(String nome) {
            this.nome = nome;
        }
    }
    
    Cliente padrao;
    final DecimalFormat df = new DecimalFormat('R$ #,###.00');
    Venda venda;
    Vendedor vendedor;
    List<RegistroDeFluxo> transacoes = new ArrayList<>();

    VendaTableModel() {
        EntityManager em = JPA.getEM();
        padrao = em.find(Cliente.class, 1);
        if (padrao == null) {
            padrao = Cliente.padrao();
            em.transaction.begin();
            padrao = em.merge(padrao);
            em.transaction.commit();
        }
        venda = new Venda(padrao.conta, Vendedor.padrao());
        em.close();
    }
    
    VendaTableModel(Venda venda){
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
        this.vendedor = vendedor;
        venda.vendedor = vendedor;
    }

    ItemVenda add(ItemVenda iv) {
        venda.addItem(iv);
        transacoes.add(iv);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
        return iv;
    }

    ItemVenda add(ItemEstoque e, int qnt) {
        ItemVenda itemVenda = ItemVenda.Build(e, qnt);
        return add(itemVenda);
    }

    ItemVenda add(String codigoBarras) throws NoResultException {
        ItemEstoque e = buscarProduto(codigoBarras);
        return add(e, 1);
    }

    void remove(int row) {
        RegistroDeFluxo item = transacoes.get(row);
        if (item instanceof ItemVenda) {
            venda.removeItem((ItemVenda) item);
        }
        transacoes.remove(row);
        this.fireTableRowsDeleted(row, row);
    }


    BigDecimal getTotal() {
        return venda.subTotal;
    }

    void setConta(Conta c) {
        venda.conta = c;
    }

    void finalizarVenda() {
              
        venda = new Venda(padrao.conta, vendedor);
        transacoes.clear();
        fireTableDataChanged();
    }

    @Override
    void pesquisar(String campo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    String getColumnName(int column) {
        return Colunas.values()[column].nome;
    }

    @Override
    Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }

    @Override
    ItemVenda getObjetoLinha(int linha) {
        venda.listaProdutos.get(linha);
    }

    @Override
    int getRowCount() {
        transacoes.size()
    }

    @Override
    int getColumnCount() {
        Colunas.values().length;
    }

    @Override
    Object getValueAt(int linha, int coluna) {
        RegistroDeFluxo item = transacoes.get(linha);
        if (item instanceof ItemVenda) {
            ItemVenda iv = venda.listaProdutos.get(linha);
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
        return null;
    }

    private ItemEstoque buscarProduto(String codigoBarras) throws NoResultException {
        EntityManager em = JPA.getEM();
        TypedQuery<ItemEstoque> tq = em.createQuery("SELECT e FROM ItemEstoque e WHERE e.codBarras = :codBarras", 
                ItemEstoque.class);
        tq.setParameter("codBarras", codigoBarras);

        ItemEstoque result = tq.getSingleResult();

        return result;

    }

    @Override
    void atualizaEspacamentoColunas() {
        super.atualizaEspacamentoColunas()
    }

    @Override
    void setJTable(JTable tabela) {
        super.setJTableColumnsWidth(tabela, 5, 12, 56, 5, 10, 10);
        tabela.showGrid = false;
        tabela.selectionMode = ListSelectionModel.SINGLE_INTERVAL_SELECTION;
    }
}


