package gerenciadordevendas.tablemodel;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

abstract class TableModelPesquisavel<T> extends AbstractTableModel implements Pesquisavel {

    T get(int row) {
        return null;
    }

    final void setJTableColumnsWidth(JTable table, double... percentages) {
        def tablePreferredWidth = (int) table.getSize().getWidth();
        double total = percentages.sum();
        
        for (int i = 0; i < table?.columnModel?.columnCount; i++) {
            TableColumn column = table.columnModel.getColumn(i);
            column.setPreferredWidth((int) (tablePreferredWidth * (percentages[i] / total)));
        }
    }

    @Override
    void pesquisar(String campo) {  }

    @Override
    abstract String getColumnName(int column)

    @Override
    abstract Class<?> getColumnClass(int columnIndex)

    @Override
    abstract int getColumnCount()
    
    void novo(java.awt.Window parent) {
        JOptionPane.showMessageDialog(parent, "Esta opção não está habilitada")
    }

    void editar(java.awt.Window parent) {
        JOptionPane.showMessageDialog(parent, "Editar não está habilitado")
    }

    void remover(java.awt.Window parent) {
        JOptionPane.showMessageDialog(parent, "Remover não está habilitado")
    }

    Object getObjetoLinha(int row) {
        return get(row);
    }
    
    void setJTable(JTable table){}
    
    void atualizaEspacamentoColunas() { }
}
