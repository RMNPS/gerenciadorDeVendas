package gerenciadordevendas.tablemodel;

import gerenciadordevendas.model.Cliente;
import gerenciadordevendas.model.Endereco
import gerenciadordevendas.telas.EnderecoPanel
import gerenciadordevendas.telas.TelaContainer
import java.awt.Window;

class EnderecoTableModel extends AbstractTableModelPesquisavel<Endereco> {
    
    List<Endereco> enderecos;
    
    private enum Colunas {
        ID ("ID"),
        ENDERECO("Endereço"),
        
        final String nome;

        private Colunas(String nome) {
            this.nome = nome;
        }
    }

    EnderecoTableModel(List<Endereco> enderecos) {
        this.enderecos = enderecos;
        carregar();
        
        setJTableColumnsWidth(800, 10, 90);
    }

    void carregar() {
        dadosBackup = dados = enderecos;
        fireTableDataChanged();
    }
    
    @Override
    protected String getJPQL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean getSeachFilter(Endereco o1, String o2) {
        return o1.toString().toLowerCase().contains(o2) || String.valueOf(o1.id).startsWith(o2);
    }

    @Override
    int getColumnCount() {
        return Colunas.values().length;
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        Endereco c = get(rowIndex);
        switch (Colunas.values()[columnIndex]) {
            case Colunas.ID:       return c.id
            case Colunas.ENDERECO: return c.toString()
            default:               return null;
        }
    }

    @Override
    String getColumnName(int column) {
        return Colunas.values()[column].nome;
    }

    @Override
    void novo(Window parent) {
        Endereco e = new Endereco();
        EnderecoPanel panel = new EnderecoPanel(e)
        new TelaContainer(parent, panel).visible = true
        if (panel.isSalvo()) {
            enderecos.add(e);
        }
        carregar();
    }

    @Override
    void editar(Window parent) {
        int row = getJTable().selectedRow;
        if (row > -1) {
            new TelaContainer(parent, new EnderecoPanel(get(row))).visible = true;
            carregar();
            getJTable().setRowSelectionInterval(row, row);
        }
    }
    
    @Override
    void remover(Window parent) {
        int row = getJTable().selectedRow;
        if (row > -1) {
            Endereco e = get(row);
            e.setDeleted();
            enderecos.remove(e);
            carregar();
        }
    }
}
