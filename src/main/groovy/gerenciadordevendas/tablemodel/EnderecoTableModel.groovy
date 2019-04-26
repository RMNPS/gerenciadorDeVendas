package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.Cliente;
import gerenciadordevendas.model.Endereco
import gerenciadordevendas.telas.EnderecoPanel
import gerenciadordevendas.telas.TelaContainer
import java.awt.Window;
import javax.persistence.EntityManager

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
    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(5, 98)
    }
    
    @Override
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
        panel.salvarVisible = true
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
            EnderecoPanel panel = new EnderecoPanel(get(row))
            panel.salvarVisible = true
            new TelaContainer(parent, panel).visible = true;
            carregar();
            getJTable().setRowSelectionInterval(row, row);
        }
    }
    
    @Override
    void remover(Window parent) {
        int row = getJTable().selectedRow;
        if (row > -1) {
            if (row == 0) {
                JOptionPane.showMessageDialog(this, "O endereço principal não pode ser removido.");
                return;
            }
            Endereco e = get(row);
            if (e.id != 0) {
                EntityManager em = JPA.getEM();
                
                em.getTransaction().begin()
                e.setDeleted(true);
                em.merge(e)
                em.getTransaction().commit()
            }
            
            enderecos.remove(e);
            carregar();
        }
    }
}
