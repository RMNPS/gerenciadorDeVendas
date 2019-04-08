/*
 * GerenciadorDeVendas: ProdutosTableModel.groovy
 * Enconding: UTF-8
 * Data de criação: 21/09/2018 11:23:07
 */

package gerenciadordevendas.tablemodel

import gerenciadordevendas.JPA;
import gerenciadordevendas.Regras;
import gerenciadordevendas.model.ItemEstoque
import gerenciadordevendas.model.ItemVenda;
import gerenciadordevendas.model.Produto;
import gerenciadordevendas.telas.TelaItemEstoque;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.JTable;

class ProdutosTableModel extends TableModelPesquisavel {

    List<ItemEstoque> produtos;
    List<ItemEstoque> produtosBackup;
    JTable table;
    final DecimalFormat df = new DecimalFormat('R$ #,##0.00')

    enum Colunas {
        ID("ID"), NOME("Nome"), TAMANHO("Tamanho"), COR("Cor"), QUANTIDADE("QNT"), PRECO_A_PRAZO("Preço a Prazo R\$")
        private String nome;

        private Colunas(String nome) {
            this.nome = nome;
        }
    }

    ProdutosTableModel() {
        carregaProdutos();
    }

    @Override
    final void setJTable(JTable table) {
        this.table = table
        setJTableColumnsWidth(table, 800, 5, 50, 15, 15, 5, 10);
    }

    
    void carregaProdutos() {
        EntityManager em = JPA.getEM();

        Query query = em.createQuery("SELECT e FROM ItemEstoque e WHERE e.deleted = FALSE");
        produtosBackup = produtos = query.getResultList();
        em.close();
        fireTableDataChanged();
    }


    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        ItemEstoque ie = produtos.get(rowIndex);
        switch (Colunas.values()[columnIndex]) {
            case Colunas.ID:            return ie.id
            case Colunas.NOME:          return ie.produto.nome
            case Colunas.TAMANHO:       return ie.tamanho?.nome
            case Colunas.COR:           return ie.cor?.nome
            case Colunas.QUANTIDADE:    return ie.quantidade
            case Colunas.PRECO_A_PRAZO: return df.format(ie.valorAprazo ?: 0g)
        }
        return null;
    }

    @Override
    void pesquisar(String campo) {
        String campoLowerCase = campo.toLowerCase();
        this.produtos = produtosBackup;
        if (!campo.isEmpty()) {
            List<Produto> result = new ArrayList<>();
            produtos.stream().filter({ie -> ie.produto.nome.toLowerCase().contains(campoLowerCase)})
                    .forEach({ie -> result << ie});
            this.produtosBackup = produtos;
            this.produtos = result;
        }
        fireTableDataChanged();
    }

    @Override
    Class<?> getColumnClass(int columnIndex) {
        if (produtos.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    Object getObjetoLinha(int row) {
        String erro = "";
        while (true) {
            String sQnt = JOptionPane.showInputDialog(erro + "Insira a quantidade", 1);
            if (sQnt == null) {
                return null;
            }
            try {
                int qnt = Integer.valueOf(sQnt);
                if (qnt < 1 || qnt > Regras.QUANTIDADE_MAXIMA_PRODUTO) {
                    erro = "A quantidade deve estar entre 1 e " + Regras.QUANTIDADE_MAXIMA_PRODUTO + ".\n";
                    continue;
                }
                println(produtos.get(row));
                return ItemVenda.Build(produtos.get(row), qnt);
            } catch (NumberFormatException e) {
                erro = "A quantidade deve estar entre 1 e " + Regras.QUANTIDADE_MAXIMA_PRODUTO + ".\n";
            }
        }
    }

    @Override
    void novo(Window parent) {
        new TelaItemEstoque(parent).setVisible(true);
        carregaProdutos();
    }

    @Override
    void editar(Window parent) {
        int row = table.selectedRow;
        if (row > -1) {
            new TelaItemEstoque(parent, produtos.get(row)).setVisible(true);
        }
    }
    
     @Override
    String getColumnName(int column) {
        return Colunas.values()[column].nome;
    }

    @Override
    int getRowCount() {
        return produtos.size();
    }

    @Override
    int getColumnCount() {
        return Colunas.values().length;
    }
}