package gerenciadordevendas.tablemodel

import gerenciadordevendas.JPA
import gerenciadordevendas.model.Empresa
import gerenciadordevendas.model.TipoEmpresa
import gerenciadordevendas.telas.TelaEmpresa

import javax.persistence.EntityManager
import javax.persistence.Query
import java.awt.*

/**
 *
 * @author ramon
 */
class FilialTableModel extends AbstractTableModelPesquisavel<Empresa> {


    FilialTableModel() {
        super( ["ID", "CNPJ", "Filial", 'Tipo'])
        carregar()
    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(5, 40, 15, 25, 15)
    }

    @Override
    protected Query getQuery(EntityManager em) {
        throw new RuntimeException("Não usar")
    }

    void carregar() {
        EntityManager em = JPA.getEM()
        dadosBackup = dados = em.createQuery("SELECT e FROM Empresa e WHERE e.tipoEmpresa = :filial OR e.tipoEmpresa = :matriz")
                .setParameter('filial', TipoEmpresa.FILIAL)
                .setParameter('matriz', TipoEmpresa.MATRIZ)
                .getResultList()
        em.close()
        fireTableDataChanged()
    }

    @Override
    protected boolean getSeachFilter(Empresa f, String campo) {
        f.nome.toLowerCase().contains(campo)
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        Empresa filial = get(rowIndex)
        switch (colunas[columnIndex]) {
            case "ID": return filial.id
            case "CNPJ": return filial.cnpj
            case "Filial": return filial.nome
            case 'Tipo' : return filial.tipoEmpresa
        }
        return null
    }

    @Override
    void novo(Window parent) {
        def telaEmpresa = new TelaEmpresa(null, "Filiais")
        telaEmpresa.empresa = new Empresa()
        telaEmpresa.empresa.tipoEmpresa = TipoEmpresa.FILIAL
        telaEmpresa.visible = true
        carregar()
    }

    @Override
    void editar(Window parent) {
        int row = getJTable().selectedRow
        if (row > -1) {
            def telaEmpresa = new TelaEmpresa(null, "Filiais")
            telaEmpresa.empresa = get(row)
            telaEmpresa.visible = true
            carregar()
        }
    }
}
