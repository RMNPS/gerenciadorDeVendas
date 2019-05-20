/*
 * GerenciadorDeVendas: AbstractTableModelPesquisavel.java
 * Enconding: UTF-8
 * Data de criação: 07/03/2018 09:13:08
 */
package gerenciadordevendas.tablemodel

import javax.persistence.EntityManager
import javax.persistence.Query
import javax.swing.*
import java.awt.*

abstract class CompleteTableModelPesquisavel<T> extends AbstractTableModelPesquisavel<T> {

    CompleteTableModelPesquisavel(def colunas) {
        super(colunas)
    }

    protected abstract Query getQuery(EntityManager em);
    protected abstract boolean getSeachFilter(T o, String campoLowerCase);
    protected abstract getValorColuna(String coluna, T campo);

    @Override
    final Object getValueAt(int rowIndex, int columnIndex) {
        getValorColuna(colunas[columnIndex], get(rowIndex))
    }


    @Override
    final void editar(Window parent) {
        int row = getJTable().getSelectedRow()
        if (row > -1) {
            editar(parent, get(row))
            carregar()
        }
    }

    protected void editar(Window parent, T campo) {
    }

    @Override
    final void remover(Window parent) {
        int row = getJTable().getSelectedRow()
        if (row > -1) {
            remover(parent, get(row))
            carregar()
        }

    }

    protected void remover(Window parent, T campo) {
        JOptionPane.showMessageDialog(null, "Remover não está habilitado")
    }
}
