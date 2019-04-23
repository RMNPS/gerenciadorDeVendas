    package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.ItemEstoque
import gerenciadordevendas.telas.TelaItemEstoque
import java.awt.Window
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import javax.swing.JTable

class ItemEstoqueTableModel extends AbstractTableModelPesquisavel<ItemEstoque> {

    enum ColunasItemEstoque {
	ID_ESTOQUE("id Estoque"),
        ID_PRODUTO("id Produto"),
        NOME("Nome"),
        FORNECEDOR("Fornecedor"),
        QUANTIDADE("QNT"),
        QUANTIDADE_PRODUTO("QNT Produto"),
        PRECO_CUSTO('R$ Custo'),
        PRECO_A_PRAZO('R$ Venda à Prazo'),
        PRECO_A_VISTA('R$ Venda à Vista');
//        NUMERO_PARCELAS('Nº Parcelas'),
//        PRECO_PARCELA('R$ Parcela'),
        //        VALIDADE("Validade");
        
        String nome;
        
        private ColunasItemEstoque(String nome) {
            this.nome = nome;
        }
    }
    
    final DecimalFormat df = new DecimalFormat('R$ #,##0.00');
    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    ItemEstoqueTableModel() {
        this(null);
    }

    ItemEstoqueTableModel(JTable tabela) {
        carregar();
        setJTable(tabela);
    }

    @Override
    final void setJTable(JTable table) {
        super.table = table;
        setJTableColumnsWidth(800, 8, 8, 41, 23, 4, 4, 12, 12, 8, 8, 8);
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
    protected String getJPQL() {
        return "SELECT e FROM ItemEstoque e WHERE e.deleted = FALSE";
    }

    @Override
    protected boolean getSeachFilter(ItemEstoque ie, String campoLowerCase) {
        return ie.produto.nome.toLowerCase().contains(campoLowerCase) || ie.codigoBarras.equals(campoLowerCase);
    }

    @Override
    String getColumnName(int column) {
        return ColunasItemEstoque.values()[column].nome;
    }

    @Override
    int getColumnCount() {
        return ColunasItemEstoque.values().length;
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        ItemEstoque ie = get(rowIndex);
        switch (ColunasItemEstoque.values()[columnIndex]) {
            case ColunasItemEstoque.ID_ESTOQUE:         return ie.id
            case ColunasItemEstoque.ID_PRODUTO:         return ie?.produto.id
            case ColunasItemEstoque.NOME:               return ie?.produto?.nome
            case ColunasItemEstoque.FORNECEDOR:         return ie?.fornecedor?.nome
            case ColunasItemEstoque.QUANTIDADE:         return ie?.quantidade
            case ColunasItemEstoque.QUANTIDADE_PRODUTO: 
                Double quantidade = JPA.getEM().createQuery("SELECT sum(e.quantidade) FROM ItemEstoque e WHERE e.deleted = FALSE and e.produto = :p")
                        .setParameter("p", ie.getProduto())
                        .getSingleResult() as Double
            
                return quantidade ?: 0d
            case ColunasItemEstoque.PRECO_CUSTO:        return df.format(ie.valorCusto)
            case ColunasItemEstoque.PRECO_A_PRAZO:      return df.format(ie.valorAprazo ?: 0g)
            case ColunasItemEstoque.PRECO_A_VISTA:      return df.format(ie.valorAvista ?: 0g)
//            case ColunasItemEstoque.NUMERO_PARCELAS:    return ie?.numeroParcelas
//            case ColunasItemEstoque.PRECO_PARCELA:      return df.format(ie.valorParcelaSugerida ?: 0g)
//            case ColunasItemEstoque.VALIDADE:           return ie.validade ? sdf.format(ie.validade) : ""
            default:                                    return null;
        }
    }
    

}
