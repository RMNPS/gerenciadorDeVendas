package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA
import gerenciadordevendas.EntityService
import gerenciadordevendas.model.Produto
import gerenciadordevendas.telas.TelaProduto

import javax.persistence.EntityManager
import javax.persistence.Query
import java.awt.Window
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import javax.swing.JOptionPane;

class ProdutoTableModel extends CompleteTableModelPesquisavel<Produto> {

    ProdutoTableModel() {
        super(['ID', 'Nome', 'QNT Produto'])

    }
    final DecimalFormat df = new DecimalFormat('R$ #,##0.00')
    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(5, 85, 10)
    }

    @Override
    void novo(Window parent) {
        TelaProduto tela = new TelaProduto(parent)
        tela.setVisible(true)
        setSelected(tela.getProduto())
        carregar()
    }

    @Override
    void editar(Window parent, Produto ie) {
        TelaProduto tela = new TelaProduto(parent, ie)
        tela.setVisible(true)
        setSelected(tela.getProduto())
    }

    @Override
    void remover(Window parent, Produto ie) {

        int resposta = JOptionPane.showConfirmDialog(parent,
                "Deseja realmente remover?\nEsta opção não poderá ser defeita.",
                "Aviso", JOptionPane.YES_NO_OPTION)
        if (resposta == JOptionPane.YES_OPTION) {
            EntityService.remove(ie)
        }
    }

    @Override
    protected Query getQuery(EntityManager em) {
        return em.createQuery("SELECT e FROM Produto e")
    }

    @Override
    protected boolean getSeachFilter(Produto ie, String campoLowerCase) {
        return ie.nome.toLowerCase().contains(campoLowerCase)
    }

    @Override
    protected getValorColuna(String coluna, Produto p) {
        switch (coluna) {
            case 'ID':              return p.id
            case 'Nome':            return p.nome
            case 'QNT Produto':
                Double quantidade = JPA.getEM().createQuery("SELECT sum(e.quantidade) FROM ItemEstoque e WHERE e.produto = :p")
                        .setParameter("p", p)
                        .getSingleResult() as Double

                                  return quantidade ?: 0d
            default:              return null
        }
    }
}
