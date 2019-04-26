package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA;
import gerenciadordevendas.EntityService;
import gerenciadordevendas.model.ItemEstoque
import gerenciadordevendas.telas.TelaItemEstoque
import java.awt.Window
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import javax.swing.JOptionPane;

class ItemEstoqueTableModel extends AbstractTableModelPesquisavel<ItemEstoque> {

    enum Colunas {
        ID_ESTOQUE("id"),
//        ID_PRODUTO("id Produto"),
        NOME("Nome"),
        FORNECEDOR("Fornecedor"),
        QUANTIDADE("QNT"),
        QUANTIDADE_PRODUTO("QNT Produto"),
        PRECO_CUSTO('Custo'),
        PRECO_A_PRAZO('Preço à Prazo'),
        PRECO_A_VISTA('Preço à Vista');
        //        NUMERO_PARCELAS('Nº Parcelas'),
        //        PRECO_PARCELA('R$ Parcela'),
        //        VALIDADE("Validade");
        
        String nome;
        
        private Colunas(String nome) {
            this.nome = nome;
        }
    }
    
    final DecimalFormat df = new DecimalFormat('R$ #,##0.00');
    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    ItemEstoqueTableModel() {
        carregar();
    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(5, 40, 15, 5, 5, 10, 10, 10)
    }

    @Override
    void novo(Window parent) {
        new TelaItemEstoque(parent).setVisible(true);
        carregar();
    }

    @Override
    void editar(Window parent) {
        int row = getJTable().getSelectedRow();
        if (row > -1){
            new TelaItemEstoque(parent, get(row)).setVisible(true);
            carregar();
        }
    }
    
    @Override
    void remover(Window parent) {
        int row = getJTable().getSelectedRow();
        if (row > -1){
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover?\nEsta opção não poderá ser defeita.", "Aviso", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                ItemEstoque ie = get(row)
                
                EntityService.remove(ie)
                carregar()
            }
        }
    }
    
    @Override
    protected String getJPQL() {
        return "SELECT e FROM ItemEstoque e WHERE e.deleted = FALSE";
    }

    @Override
    protected boolean getSeachFilter(ItemEstoque ie, String campoLowerCase) {
        return ie.produto.nome.toLowerCase().contains(campoLowerCase) || ie.codigoBarras.equals(campoLowerCase);
    }

    @Override
    String getColumnName(int column) {
        return Colunas.values()[column].nome;
    }

    @Override
    int getColumnCount() {
        return Colunas.values().length;
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        ItemEstoque ie = get(rowIndex);
        switch (Colunas.values()[columnIndex]) {
        case Colunas.ID_ESTOQUE:         return ie.id
//        case Colunas.ID_PRODUTO:         return ie?.produto.id
        case Colunas.NOME:               return ie?.produto?.nome + " " +ie?.cor.nome +" "+ ie?.tamanho.nome
        case Colunas.FORNECEDOR:         return ie?.fornecedor?.nome
        case Colunas.QUANTIDADE:         return ie?.quantidade
        case Colunas.QUANTIDADE_PRODUTO:
            Double quantidade = JPA.getEM().createQuery("SELECT sum(e.quantidade) FROM ItemEstoque e WHERE e.deleted = FALSE and e.produto = :p")
            .setParameter("p", ie.getProduto())
            .getSingleResult() as Double
            
            return quantidade ?: 0d
        case Colunas.PRECO_CUSTO:        return df.format(ie.valorCusto)
        case Colunas.PRECO_A_PRAZO:      return df.format(ie.valorAprazo ?: 0g)
        case Colunas.PRECO_A_VISTA:      return df.format(ie.valorAvista ?: 0g)
            //            case Colunas.NUMERO_PARCELAS:    return ie?.numeroParcelas
            //            case Colunas.PRECO_PARCELA:      return df.format(ie.valorParcelaSugerida ?: 0g)
            //            case Colunas.VALIDADE:           return ie.validade ? sdf.format(ie.validade) : ""
        default:                                    return null;
        }
    }
    

}
