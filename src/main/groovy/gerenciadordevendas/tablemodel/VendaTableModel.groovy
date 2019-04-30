/*
 * GerenciadorDeVendas: VendaTableModel.groovy
 * Enconding: UTF-8
 * Data de criação: 30/04/2019 09:49:54
 */

package gerenciadordevendas.tablemodel

import gerenciadordevendas.model.Venda
import gerenciadordevendas.telas.TelaPagamento
import java.awt.Window
import java.text.DecimalFormat
import java.text.SimpleDateFormat

/**
 *
 * @author Ramon Porto
 */
class VendaTableModel extends AbstractTableModelPesquisavel<Venda> {

    enum Colunas {
        ID_VENDA("id"),
        DATA("Data"),
        CLIENTE("Cliente"),
        TOTAL("Total"),
        ESTADO("Estado"),
        ULTIMA_ALTERACAO("Última Alteração");
        String nome;
        
        private Colunas(String nome) {
            this.nome = nome;
        }
    }
    
    final DecimalFormat df = new DecimalFormat('R$ #,##0.00');
    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    VendaTableModel() {
        carregar();
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
        if (row > -1){
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover?\nEsta opção não poderá ser defeita.", "Aviso", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                Venda v = get(row)
                
                EntityService.remove(v)
                carregar()
            }
        }
    }
    
    @Override
    protected String getJPQL() {
        return "SELECT e FROM Venda e";
    }

    @Override
    protected boolean getSeachFilter(Venda v, String campoLowerCase) {
        return v.cliente.nome.toLowerCase().contains(campoLowerCase) || VendaTableModel.equals(""+ v.id);
    }

    @Override
    String getColumnName(int column) {
        return Colunas.values()[column].nome;
    }

    @Override
    int getColumnCount() {
        return Colunas.values().length;
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        Venda v = get(rowIndex);
        switch (Colunas.values()[columnIndex]) {
            case Colunas.ID_VENDA:  return v.id
            case Colunas.DATA: return v.dataCriacao ? sdf.format(v.dataCriacao) : ""
            case Colunas.CLIENTE:   return v.conta.cliente.nome
            case Colunas.TOTAL:     return df.format(v.total ?: 0g)
            case Colunas.ESTADO:    return v.estado
            case Colunas.ULTIMA_ALTERACAO: return v.dataAtualizacao ? sdf.format(v.dataAtualizacao) : ""
            default:                return null;
        }
    }

}


