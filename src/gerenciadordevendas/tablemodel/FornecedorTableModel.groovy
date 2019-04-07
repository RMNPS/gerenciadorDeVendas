package gerenciadordevendas.tablemodel;

import gerenciadordevendas.model.Fornecedor;
import gerenciadordevendas.telas.TelaFornecedor;
import java.awt.Window;
import javax.swing.JTable;

/**
 *
 * @author ramon
 */
class FornecedorTableModel extends AbstractTableModelPesquisavel<Fornecedor> {

    enum ColunasFornecedor {
        ID("ID"),
        FORNECEDOR("Fornecedor"),
        TELEFONE("Telefone"),
        VENDEDOR("Vendedor"),
        TELEFONE_VENDEDOR("Telefone Vendedor");
        
        private final String name;

        private ColunasFornecedor(String name) {
            this.name = name
        }
    }

    FornecedorTableModel() {
        carregar();
    }

    @Override
    void setJTable(JTable table) {
        super.table = table
        setJTableColumnsWidth(800, 5, 45, 10, 30, 10)
    }
    
    @Override
    protected String getJPQL() {
        return "SELECT e FROM Fornecedor e WHERE e.deleted = FALSE"
    }

    @Override
    protected boolean getSeachFilter(Fornecedor f, String campo) {
        return f.nome.toLowerCase().contains(campo)
    }

    @Override
    String getColumnName(int column) {
        return ColunasFornecedor.values()[column].name
    }

    @Override
    int getColumnCount() {
        return ColunasFornecedor.values().length
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        Fornecedor fornecedor = get(rowIndex)
        switch (ColunasFornecedor.values()[columnIndex]) {
            case ColunasFornecedor.ID:                return fornecedor.id
            case ColunasFornecedor.FORNECEDOR:        return fornecedor.nome
            case ColunasFornecedor.TELEFONE:          return fornecedor.telefone
            case ColunasFornecedor.VENDEDOR:          return fornecedor.vendedor
            case ColunasFornecedor.TELEFONE_VENDEDOR: return fornecedor.telefoneVendedor
        }
        return null
    }

    @Override
    void novo(Window parent) {
        new TelaFornecedor().setVisible(true);
        carregar();
    }

    @Override
    void editar(Window parent) {
        int row = getJTable().getSelectedRow();
        if (row > -1) {
            new TelaFornecedor(parent, get(row)).setVisible(true);
            carregar();
        }
    }
}
