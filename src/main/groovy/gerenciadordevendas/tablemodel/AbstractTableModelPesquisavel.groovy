/*
 * GerenciadorDeVendas: AbstractTableModelPesquisavel.java
 * Enconding: UTF-8
 * Data de criação: 07/03/2018 09:13:08
 */
package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JTable;

abstract class AbstractTableModelPesquisavel<T> extends TableModelPesquisavel {
    
    List<T> dados = new ArrayList<>();
    List<T> dadosBackup = new ArrayList<>();
    JTable table;
    
    protected abstract String getJPQL();
    protected abstract boolean getSeachFilter(T o, String campoLowerCase);

    @Override
    abstract String getColumnName(int column) ;

    @Override
    abstract int getColumnCount() ;

    @Override
    abstract Object getValueAt(int rowIndex, int columnIndex) ;
    
    @Override
    T get(int row) {
        return dados.get(row);
    }
    
    List<T> getSelectedObjects() {
        int[] rows = table.getSelectedRows();
        List<T> objects = new ArrayList<>()
        for (int row : rows) {
            objects.add(get(row))
        }
        return objects
    }
    
    void carregar() {
        EntityManager em = JPA.getEM();
        dadosBackup = dados = em.createQuery(getJPQL()).getResultList();
        em.close();
        fireTableDataChanged();
    }

    final void setJTableColumnsWidth(double... percentages) {
        super.setJTableColumnsWidth(table, percentages);
    }
    
    @Override
    Class<?> getColumnClass(int columnIndex) {
        if (dados.isEmpty() || getValueAt(0, columnIndex) == null) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    void pesquisar(String campo) {
        String campoLowerCase = campo.toLowerCase();
        this.dados = dadosBackup;

        if (!campo.isEmpty()) {
            List<T> result = new ArrayList<>();
            dados.stream().filter({item -> (getSeachFilter(item, campoLowerCase))}).forEach({item -> result.add(item)});
            this.dadosBackup = dados;
            this.dados = result;
        }
        fireTableDataChanged();
    }

    @Override
    void setJTable(JTable table) {
        this.table = table;
        table?.model = this;
        table?.autoCreateRowSorter = true;
    }
    
    JTable getJTable() {
        table
    }

    @Override
    int getRowCount() {
        dados.size()
    }
}
