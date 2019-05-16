package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA;
import gerenciadordevendas.EntityService
import gerenciadordevendas.model.Fornecedor;
import gerenciadordevendas.model.ItemEstoque
import gerenciadordevendas.telas.TelaItemEstoque

import javax.persistence.EntityManager
import javax.persistence.Query
import java.awt.Window
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import javax.swing.JOptionPane;

class ItemEstoqueTableModel extends AbstractTableModelPesquisavel<ItemEstoque> {

//    def colunas = ['id', 'Código de Baras', 'id Produto', 'Nome',
//                   'Fornecedor', 'Tamanho','Cor' ,'QNT', 'QNT Produto', 'Custo', 'Preço à Prazo', 'Preço à Vista', 'Nº Parcelas', 'R$ Parcela', 'Validade']


    ItemEstoqueTableModel(produto) {
        super(produto? ['ID', 'Nome', 'Tamanho', 'Cor', 'QNT', 'Preço a Prazo R$'] : ['id', 'Código de Baras', 'Nome',
                                         'Fornecedor', 'QNT', 'Custo', 'Preço à Prazo', 'Preço à Vista']);

    }
    final DecimalFormat df = new DecimalFormat('R$ #,##0.00');
    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(5, 7, 37, 15, 5, 10, 10, 10)
    }

    @Override
    void novo(Window parent) {
        new TelaItemEstoque(parent).setVisible(true);
        carregar();
    }

    @Override
    void editar(Window parent) {
        int row = getJTable().getSelectedRow();
        if (row > -1) {
            new TelaItemEstoque(parent, get(row)).setVisible(true);
            carregar();
        }
    }

    @Override
    void remover(Window parent) {
        int row = getJTable().getSelectedRow();
        if (row > -1) {
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover?\nEsta opção não poderá ser defeita.", "Aviso", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                ItemEstoque ie = get(row)

                EntityService.remove(ie)
                carregar()
            }
        }
    }

    @Override
    protected Query getQuery(EntityManager em) {
        return em.createQuery("SELECT e FROM ItemEstoque e WHERE e.deleted = FALSE")
    }

    @Override
    protected boolean getSeachFilter(ItemEstoque ie, String campoLowerCase) {
        return ie.produto.nome.toLowerCase().contains(campoLowerCase) || ie.codigoBarras.equals(campoLowerCase);
    }


    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
         ItemEstoque ie = get(rowIndex);
        switch (colunas[columnIndex]) {
            case 'id': return ie.id
            case 'Código de Baras': return ie.codigoBarras
            case 'id Produto': return ie?.produto.id
            case 'Nome': return ie?.produto?.nome + " " + ie?.cor.nome + " " + ie?.tamanho.nome
            case 'Fornecedor': return ie?.fornecedor?.nome
            case 'Tamanho':          return ie.tamanho?.nome
            case 'Cor':              return ie.cor?.nome
            case 'QNT': return ie?.quantidade
            case 'QNT Produto':
                Double quantidade = JPA.getEM().createQuery("SELECT sum(e.quantidade) FROM ItemEstoque e WHERE e.deleted = FALSE and e.produto = :p")
                        .setParameter("p", ie.getProduto())
                        .getSingleResult() as Double

                return quantidade ?: 0d
            case 'Custo': return df.format(ie.valorCusto)
            case 'Preço à Prazo': return df.format(ie.valorAprazo ?: 0g)
            case 'Preço à Vista': return df.format(ie.valorAvista ?: 0g)
            case 'Nº Parcelas': return ie?.numeroParcelas
            case 'R$ Parcela': return df.format(ie.valorParcelaSugerida ?: 0g)
            case 'Validade': return ie.validade ? sdf.format(ie.validade) : ""
            default: return null;
        }
    }
}
