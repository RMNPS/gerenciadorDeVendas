/*
 * GerenciadorDeVendas: CartaoTableModel.java
 * Enconding: UTF-8
 * Data de criação: 26/02/2018 09:30:03
 */
package gerenciadordevendas.tablemodel

import gerenciadordevendas.JPA
import gerenciadordevendas.model.Cartao
import gerenciadordevendas.model.Cliente
import java.awt.Component
import java.awt.Window
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.text.SimpleDateFormat
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.swing.AbstractCellEditor
import javax.swing.JButton
import javax.swing.JTable
import javax.swing.UIManager
import javax.swing.table.TableCellEditor
import javax.swing.table.TableCellRenderer
import javax.swing.table.TableColumnModel

class CartaoTableModel extends TableModelPesquisavel<Cartao> {

    static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy")
    List<Cartao> cartoes;
    final Cliente cliente;
    JTable table;



    CartaoTableModel(Cliente cliente) {
        super(['id', 'data', 'Código de Barras', 'Imprimir'])
        this.cliente = cliente;
        carregar();
    }

    final void carregar() {
        EntityManager em = JPA.getEM();
        TypedQuery<Cartao> query = em.createQuery("SELECT c FROM Cartao c WHERE c.cliente = :idCliente AND c.deleted = FALSE",
                Cartao.class).setParameter("idCliente", cliente);
        cartoes = query.getResultList();
        this.fireTableDataChanged();
    }

    class ButtonColumn extends AbstractCellEditor
            implements TableCellRenderer, TableCellEditor, ActionListener {

        JTable table;
        JButton renderButton;
        JButton editButton;
        String text;

        ButtonColumn(JTable table, int column) {
            super();
            this.table = table;
            renderButton = new JButton();

            editButton = new JButton();
            editButton.focusPainted = false;
            editButton.addActionListener(this);

            TableColumnModel columnModel = table.columnModel;
            columnModel.getColumn(column).cellRenderer = this;
            columnModel.getColumn(column).cellEditor = this;
        }

        @Override
        Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (!hasFocus && isSelected) {
                renderButton.foreground = table.selectionForeground;
                renderButton.background = table.selectionBackground;
            } else {
                renderButton.foreground = table.foreground;
                renderButton.background = UIManager.getColor("Button.background");
            }
            renderButton.text = Optional.ofNullable(value).orElse("").toString();
            return renderButton;
        }

        @Override
        Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected, int row, int column) {
            text = (value == null) ? "" : value.toString();
            editButton.text = text;
            return editButton;
        }

        @Override
        Object getCellEditorValue() {
            return text;
        }

        @Override
        void actionPerformed(ActionEvent e) {
            fireEditingStopped();
            System.out.println(e.actionCommand + " : " + table.selectedRow);

            int selectedRow = table.selectedRow
            if (selectedRow > -1) {
                Cartao cartao = cartoes.get(selectedRow)
                cartao.imprimir()
            }
        }
    }

    @Override
    void setJTable(JTable table) {
        this.table = table
        table.model = this
        ButtonColumn buttonColumn = new ButtonColumn(table, Colunas.IMPRIMIR.ordinal());

    }


    @Override
    boolean isCellEditable(int rowIndex, int columnIndex) {
        columnIndex == 3
    }

    @Override
    Class<?> getColumnClass(int columnIndex) {
        if (cartoes.isEmpty()) {
            return Object.class
        }
        if (columnIndex == 3) {
            return JButton.class
        }
        return getValueAt(0, columnIndex).getClass()
    }

    @Override
    int getRowCount() {
        cartoes.size()
    }


    @Override
    Object getValueAt(int rowIndex, int columnIndex) {

        Cartao cartao = cartoes.get(rowIndex)
        switch (colunas[columnIndex]) {
            case 'id': return cartao.id
            case 'data': return cartao.dataCriacao == null ? "" : SDF.format(cartao.dataCriacao)
            case 'Código de Barras': return cartao.codigoBarras
            case 'Imprimir': return 'Imprimir'
        }

        return null
    }

    @Override
    void novo(Window parent) {
        Cartao novo = new Cartao(cliente)

        EntityManager em = JPA.getEM()
        em.transaction.begin()
        novo = em.merge(novo)
        em.transaction.commit()
        em.close()

        cartoes << novo
        fireTableRowsInserted(cartoes.size() - 1, cartoes.size() - 1)
    }

    @Override
    void remover(Window parent) {
        int selectedRow = table.selectedRow
        if (selectedRow > -1) {
            Cartao cartao = cartoes.get(selectedRow)
            cartao.deleted = true
            EntityManager em = JPA.getEM()
            em.transaction.begin()
            cartao = em.merge(cartao)
            em.transaction.commit()
            em.close()

            cartoes.remove(cartao)

            fireTableRowsDeleted(selectedRow, selectedRow)
        }
    }

}
