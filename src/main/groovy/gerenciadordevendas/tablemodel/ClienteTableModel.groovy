package gerenciadordevendas.tablemodel;

import gerenciadordevendas.model.Cliente;
import gerenciadordevendas.telas.TelaCliente;
import java.awt.Window;

class ClienteTableModel extends AbstractTableModelPesquisavel<Cliente> {
    
    private enum Colunas {
        ID ("ID"),
        CPF_CNPJ("CPF/CNPJ"),
        NOME("Nome"),
        OBSERVACOES("Observações");
        
        final String nome;

        private Colunas(String nome) {
            this.nome = nome;
        }
    }

    ClienteTableModel() {
        carregar()
    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(10, 45, 45)
    }

    @Override
    protected String getJPQL() {
        return "SELECT e FROM Cliente e WHERE e.deleted = FALSE";
    }

    @Override
    protected boolean getSeachFilter(Cliente o1, String o2) {
        return o1.nome.toLowerCase().contains(o2) || String.valueOf(o1.id).startsWith(o2);
    }

    @Override
    int getColumnCount() {
        return Colunas.values().length;
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        Cliente c = get(rowIndex);
        if (c.pessoaFisica) {
            switch (Colunas.values()[columnIndex]) {
                case Colunas.ID:          return c.id
                case Colunas.CPF_CNPJ:    return c.pessoaFisica.cpf
                case Colunas.NOME:        return c.pessoaFisica.nome
                case Colunas.OBSERVACOES: return c.observacoes
                default:                  return null;
            }
        } else {
            switch (Colunas.values()[columnIndex]) {
                case Colunas.ID:          return c.id
                case Colunas.CPF_CNPJ:    return c.empresa.cpnj
                case Colunas.NOME:        return c.empresa.nome
                case Colunas.OBSERVACOES: return c.observacoes
                default:                  return null;
            }
        }
        
    }

    @Override
    String getColumnName(int column) {
        return Colunas.values()[column].nome;
    }

    @Override
    void novo(Window parent) {
        new TelaCliente(parent, new Cliente()).visible = true;
        carregar();
    }

    @Override
    void editar(Window parent) {
        int row = getJTable().selectedRow;
        if (row > -1) {
            new TelaCliente(parent, get(row)).visible = true;
            carregar();
            getJTable().setRowSelectionInterval(row, row);
        }
    }

}
