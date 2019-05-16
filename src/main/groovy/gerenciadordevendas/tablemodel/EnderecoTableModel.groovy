package gerenciadordevendas.tablemodel

import gerenciadordevendas.JPA
import gerenciadordevendas.model.Endereco
import gerenciadordevendas.telas.PanelEndereco
import gerenciadordevendas.telas.TelaContainer

import javax.persistence.EntityManager
import javax.persistence.Query
import javax.swing.*
import java.awt.*
import java.util.List

class EnderecoTableModel extends AbstractTableModelPesquisavel<Endereco> {

    List<Endereco> enderecos

    EnderecoTableModel(List<Endereco> enderecos) {
        super(['ID', 'Endereço'])
        this.enderecos = enderecos
        carregar()
    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(5, 98)
    }
    
    @Override
    void carregar() {
        dadosBackup = dados = enderecos
        fireTableDataChanged()
    }

    @Override
    protected Query getQuery(EntityManager em) {
        throw new UnsupportedOperationException("Not supported yet.")
    }

    @Override
    protected boolean getSeachFilter(Endereco o1, String o2) {
        return o1.toString().toLowerCase().contains(o2) || String.valueOf(o1.id).startsWith(o2)
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        Endereco c = get(rowIndex)
        switch (colunas[columnIndex]) {
            case 'ID':       return c.id
            case 'Endereço': return c.toString()
            default:               return null
        }
    }

    @Override
    void novo(Window parent) {
        Endereco e = new Endereco()
        PanelEndereco panel = new PanelEndereco(e)
        panel.salvarVisible = true
        new TelaContainer(parent, panel).visible = true
        if (panel.isSalvo()) {
            enderecos.add(e)
        }
        carregar()
    }

    @Override
    void editar(Window parent) {
        int row = getJTable().selectedRow
        if (row > -1) {
            PanelEndereco panel = new PanelEndereco(get(row))
            panel.salvarVisible = true
            new TelaContainer(parent, panel).visible = true
            carregar()
            getJTable().setRowSelectionInterval(row, row)
        }
    }
    
    @Override
    void remover(Window parent) {
        int row = getJTable().selectedRow
        if (row > -1) {
            if (row == 0) {
                JOptionPane.showMessageDialog(null, "O endereço principal não pode ser removido.")
                return
            }
            Endereco e = get(row)
            if (e.id != 0) {
                EntityManager em = JPA.getEM()
                
                em.getTransaction().begin()
                e.setDeleted(true)
                em.merge(e)
                em.getTransaction().commit()
            }
            
            enderecos.remove(e)
            carregar()
        }
    }
}
