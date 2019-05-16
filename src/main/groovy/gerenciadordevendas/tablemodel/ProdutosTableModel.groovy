/*
 * GerenciadorDeVendas: ProdutosTableModel.groovy
 * Enconding: UTF-8
 * Data de criação: 21/09/2018 11:23:07
 */

package gerenciadordevendas.tablemodel

import gerenciadordevendas.model.ItemEstoque
import gerenciadordevendas.telas.TelaItemEstoque

import javax.persistence.EntityManager
import javax.persistence.Query
import java.awt.Window
import java.text.DecimalFormat
import javax.swing.JTable

class ProdutosTableModel extends AbstractTableModelPesquisavel<ItemEstoque> {

    JTable table
    final DecimalFormat df = new DecimalFormat('R$ #,##0.00')


    ProdutosTableModel() {
        super(['ID', 'Nome', 'Tamanho', 'Cor', 'QNT', 'Preço a Prazo R$'])
    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(5, 50, 15, 15, 5, 10)
    }


    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        ItemEstoque ie = get(rowIndex)

        switch (colunas[columnIndex]) {
            case 'ID':               return ie.id
            case 'Nome':             return ie.produto.nome
            case 'Tamanho':          return ie.tamanho?.nome
            case 'Cor':              return ie.cor?.nome
            case 'QNT':              return ie.quantidade
            case 'Preço a Prazo R$': return df.format(ie.valorAprazo ?: 0g)
        }
        return null
    }

    @Override
    protected Query getQuery(EntityManager em) {
        em.createQuery("SELECT e FROM ItemEstoque e")
    }

    @Override
    protected boolean getSeachFilter(ItemEstoque ie, String campoLowerCase) {
        return ie.produto.nome.toLowerCase().contains(campoLowerCase)
    }


    @Override
    Object getObjetoLinha(int row) {
       get(row)
    }

    @Override
    void novo(Window parent) {
        new TelaItemEstoque(parent).setVisible(true)
        carregar()
    }

    @Override
    void editar(Window parent) {
        int row = table.selectedRow
        if (row > -1) {
            new TelaItemEstoque(parent, get(row)).setVisible(true)
            carregar()
        }
    }
}