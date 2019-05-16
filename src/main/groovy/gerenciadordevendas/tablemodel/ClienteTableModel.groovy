package gerenciadordevendas.tablemodel

import gerenciadordevendas.JPA
import gerenciadordevendas.model.Cliente
import gerenciadordevendas.model.Empresa
import gerenciadordevendas.model.PessoaFisica
import gerenciadordevendas.model.TipoEmpresa
import gerenciadordevendas.telas.TelaCliente
import gerenciadordevendas.telas.TelaEmpresa

import javax.persistence.EntityManager
import javax.persistence.Query
import java.awt.Window
import javax.swing.JOptionPane

class ClienteTableModel extends AbstractTableModelPesquisavel<Cliente> {

    ClienteTableModel() {
        super(['ID', 'CPF/CNPJ', 'Nome', 'Observações'])
    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(5, 20, 45, 30)
    }

    @Override
    protected Query getQuery(EntityManager em) {
        return em.createQuery("SELECT e FROM Cliente e")
    }

    @Override
    protected boolean getSeachFilter(Cliente o1, String o2) {
        def nome = o1.pessoaFisica ? o1.pessoaFisica.nome : o1.empresa.nome
        return nome.toLowerCase().contains(o2) || String.valueOf(o1.id).startsWith(o2)
    }


    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        Cliente c = get(rowIndex)
        if (c.pessoaFisica) {
            switch (colunas[columnIndex]) {
                case 'ID': return c.id
                case 'CPF/CNPJ': return c.pessoaFisica.cpf
                case 'Nome': return c.pessoaFisica.nome
                case 'Observações': return c.observacoes
            }
        } else {
            switch (colunas[columnIndex]) {
                case 'ID': return c.id
                case 'CPF/CNPJ': return c.empresa.cnpj
                case 'Nome': return c.empresa.nome
                case 'Observações': return c.observacoes
            }
        }
        return null
    }

    @Override
    void novo(Window parent) {
        String resposta = JOptionPane.showOptionDialog(null, "Informe o tipo de cliente você deseja criar", "Criar cliente", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ["Pessoa Jurídica", "Pessoa Física"] as String[], "Pessoa Física")
        Cliente c = new Cliente()
        if (resposta == "1") {
            TelaCliente telaClienteFisico = new TelaCliente(null, new PessoaFisica())
            telaClienteFisico.visible = true

            if (telaClienteFisico.pessoaFisica?.id != 0) {
                c.pessoaFisica = telaClienteFisico.pessoaFisica
                JPA.autoMerge { em -> em.merge(c) }
            }
        } else {
            TelaEmpresa telaEmpresa = new TelaEmpresa(null, "Cliente Jurídico")
            telaEmpresa.empresa = new Empresa()
            telaEmpresa.empresa.tipoEmpresa = TipoEmpresa.CLIENTE
            telaEmpresa.visible = true
            if (telaEmpresa.empresa?.id != 0) {
                c.empresa = telaEmpresa.empresa
                JPA.autoMerge { em -> em.merge(c) }
            }
        }
        carregar()
    }

    @Override
    void editar(Window parent) {
        int row = getJTable().selectedRow
        if (row > -1) {
            Cliente c = get(row)
            if (c.pessoaFisica) {
                TelaCliente telaClienteFisico = new TelaCliente(null, c.pessoaFisica)
                telaClienteFisico.visible = true
            } else {
                TelaEmpresa telaEmpresa = new TelaEmpresa(null, "Cliente Jurídico")
                telaEmpresa.empresa = c.empresa
                telaEmpresa.visible = true
            }
            carregar()
            getJTable().setRowSelectionInterval(row, row)
        }
    }

}
