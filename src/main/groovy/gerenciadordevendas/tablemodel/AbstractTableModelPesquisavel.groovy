/*
 * GerenciadorDeVendas: AbstractTableModelPesquisavel.java
 * Enconding: UTF-8
 * Data de criação: 07/03/2018 09:13:08
 */
package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA

import javax.persistence.EntityManager
import javax.persistence.Query;
import javax.swing.JTable;

abstract class AbstractTableModelPesquisavel<T> extends TableModelPesquisavel {

    List<T> dados = new ArrayList<>()
    List<T> dadosBackup = new ArrayList<>()
    JTable table


    AbstractTableModelPesquisavel(def colunas) {
        super(colunas)
        carregar()
    }
    
    protected abstract Query getQuery(EntityManager em);
    protected abstract boolean getSeachFilter(T o, String campoLowerCase);


    @Override
    abstract Object getValueAt(int rowIndex, int columnIndex) ;
    
    @Override
    T get(int row) {
        return dados[row]
    }
    
    List<T> getSelectedObjects() {
        table.getSelectedRows().collect {get(it)}
    }
    
    void carregar() {
        EntityManager em = JPA.getEM()
        dadosBackup = dados = getQuery(em)?.resultList ?: []
        em.close()
        fireTableDataChanged()
    }

    final void setJTableColumnsWidth(double... percentages) {
        super.setJTableColumnsWidth(table, percentages)
    }
    
    @Override
    Class<?> getColumnClass(int columnIndex) {
        if (dados.isEmpty() || getValueAt(0, columnIndex) == null) {
            return Object.class
        }
        return getValueAt(0, columnIndex).class
    }

    @Override
    void pesquisar(String campo) {
        String campoLowerCase = campo.toLowerCase()
        this.dados = dadosBackup

        if (!campo.isEmpty()) {
            List<T> result = new ArrayList<>()
            dados.stream().filter({item -> (getSeachFilter(item, campoLowerCase))}).forEach({item -> result.add(item)})
            this.dadosBackup = dados
            this.dados = result
        }
        fireTableDataChanged()
    }

    @Override
    void setJTable(JTable table) {
        this.table = table
        table?.model = this
        table?.autoCreateRowSorter = true
    }
    
    JTable getJTable() {
        table
    }

    @Override
    int getRowCount() {
        dados.size()
    }
}
