/*
 * GerenciadorDeVendas: VendaTableModel.groovy
 * Enconding: UTF-8
 * Data de criação: 30/04/2019 09:49:54
 */

package gerenciadordevendas.tablemodel

import gerenciadordevendas.EntityService
import gerenciadordevendas.model.Venda
import gerenciadordevendas.telas.TelaPagamento

import javax.persistence.EntityManager
import javax.persistence.Query
import javax.swing.JOptionPane
import java.awt.Window
import java.text.DecimalFormat
import java.text.SimpleDateFormat

/**
 *
 * @author Ramon Porto
 */
class VendaTableModel extends AbstractTableModelPesquisavel<Venda> {

    final DecimalFormat df = new DecimalFormat('R$ #,##0.00');
    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    VendaTableModel() {
        super(["id", "Data", "Cliente", "Total", "Estado", "Última Alteração"])
        carregar()
    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(5, 7, 37, 15, 5, 10, 10, 10)
    }

//    @Override
//    void novo(Window parent) {
//        new TelaPagameto(parent).setVisible(true);
//        carregar();
//    }

    @Override
    void editar(Window parent) {
        int row = getJTable().getSelectedRow();
        if (row > -1) {
            new TelaPagamento(get(row)).setVisible(true);
            carregar();
        }
    }

    @Override
    void remover(Window parent) {
        int row = getJTable().getSelectedRow();
        if (row > -1) {
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover?\nEsta opção não poderá ser defeita.", "Aviso", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                Venda v = get(row)

                EntityService.remove(v)
                carregar()
            }
        }
    }

    @Override
    protected Query getQuery(EntityManager em) {
        return em.createQuery ("SELECT e FROM Venda e")
    }

    @Override
    protected boolean getSeachFilter(Venda v, String campoLowerCase) {
        return v.conta.cliente.nome.toLowerCase().contains(campoLowerCase) || VendaTableModel.equals("" + v.id);
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        Venda v = get(rowIndex)

        switch (colunas[columnIndex]) {
            case "id": return v.id
            case "Data": return v.dataCriacao ? sdf.format(v.dataCriacao) : ""
            case "Cliente": return v.conta.cliente.nome
            case "Total": return df.format(v.total ?: 0g)
            case "Estado": return v.estado
            case "Última Alteração": return v.dataAtualizacao ? sdf.format(v.dataAtualizacao) : ""
            default: return null;
        }
    }

}


