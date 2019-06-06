package gerenciadordevendas.tablemodel

import gerenciadordevendas.JPA
import gerenciadordevendas.model.Vendedor
import gerenciadordevendas.model.Empresa
import gerenciadordevendas.model.PessoaFisica
import gerenciadordevendas.model.TipoEmpresa
import gerenciadordevendas.telas.TelaCliente
import gerenciadordevendas.telas.TelaEmpresa

import javax.persistence.EntityManager
import javax.persistence.Query
import java.awt.Window
import javax.swing.JOptionPane

class VendedorTableModel extends AbstractTableModelPesquisavel<Vendedor> {

    VendedorTableModel() {
        super(['ID', 'CPF/CNPJ', 'Nome', 'Observações'])
    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(5, 20, 45, 30)
    }

    @Override
    protected Query getQuery(EntityManager em) {
        return em.createQuery("SELECT e FROM Vendedor e")
    }

    @Override
    protected boolean getSeachFilter(Vendedor o1, String o2) {
        def nome = o1.pessoaFisica ? o1.pessoaFisica.nome : o1.empresa.nome
        return nome.toLowerCase().contains(o2) || String.valueOf(o1.id).startsWith(o2)
    }


    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        Vendedor v = get(rowIndex)
        if (c.pessoaFisica) {
            switch (colunas[columnIndex]) {
                case 'ID': return v.id
                case 'CPF/CNPJ': return v.pessoaFisica.cpf
                case 'Nome': return v.pessoaFisica.nome
                case 'Observações': return v.observacoes
            }
        } else {
            switch (colunas[columnIndex]) {
                case 'ID': return v.id
                case 'CPF/CNPJ': return v.empresa.cnpj
                case 'Nome': return v.empresa.nome
                case 'Observações': return v.observacoes
            }
        }
        return null
    }

    @Override
    void novo(Window parent) {
        String resposta = JOptionPane.showOptionDialog(null, "Informe o tipo de cliente você deseja criar", "Criar cliente", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ["Pessoa Jurídica", "Pessoa Física"] as String[], "Pessoa Física")
        Vendedor c = new Vendedor()
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
