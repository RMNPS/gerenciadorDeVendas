package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.ItemVenda;
import gerenciadordevendas.model.Produto;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JTable;

/**
 *
 * @author ramon
 */
public class EstoqueAbcTableModel extends TableModelPesquisavel {
    static final long MILLIS_IN_30_DAYS = 30l * 24l * 60l * 60l * 1000l
    static final DecimalFormat DF = new DecimalFormat('R$ #,##0.00')
    
    List<ItemModel> produtos = new ArrayList<>()
    BigDecimal totalProdutos

    class ItemModel {

        Produto produto
        double quantidade
        BigDecimal valorUnitario
        BigDecimal porcentagemAcumulada
        String classificacao

        void add(double quantidade) {
            this.quantidade += quantidade
        }

        BigDecimal getTotal() {
            return valorUnitario * BigDecimal.valueOf(quantidade)
        }

        BigDecimal getPorcentagem() {
            return getTotal().divide(totalProdutos, new MathContext(3))
        }
    }

    enum Colunas {
        ID_PRODUTO("id Produto"),
        NOME("Nome"),
        QUANTIDADE("QNT Vendida"),
        TOTAL('R$ Total Venda'),
        PORCENTAGEM("% do Total"),
        PORCENTAGEM_ACUMULADA("% Acumulada"),
        CLASSIFICACAO("Classe");

        private final String nome;

        private Colunas(String nome) {  this.nome = nome;  }
    }

    EstoqueAbcTableModel() {
        this(null)
    }

    public EstoqueAbcTableModel(JTable tabela) {
        carregarUltimos30dias()
//        setJTable(tabela);

    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(10, 35, 10, 15, 10, 10, 10)
    }

    private void carregarUltimos30dias() {
        Date atual = new Date();
        
        long dateMenos30 = (atual.getTime() - MILLIS_IN_30_DAYS);
        carregar(new Date(dateMenos30), atual);
    }

    public final void carregar(Date inicio, Date fim) {
        produtos.clear();
        totalProdutos = BigDecimal.ZERO;
        EntityManager em = JPA.getEM();

        Query query = em.createQuery("SELECT e FROM ItemVenda e WHERE e.dataCriacao BETWEEN :inicio AND :fim  AND e.deleted = FALSE")
            .setParameter("inicio", inicio)
            .setParameter("fim", fim);
        List<ItemVenda> itensVenda = query.getResultList();
        em.close();
        
        adicionaProdutos(itensVenda);
        calculaTotal();
        produtos.sort({c1, c2 -> c2.porcentagem.compareTo(c1.porcentagem)});

        classificaProdutos();

        fireTableDataChanged();
    }

    void classificaProdutos() {
        def acumulado = 0g;
        for (ItemModel im : produtos) {
            acumulado = acumulado + im.porcentagem;
            im.porcentagemAcumulada = acumulado;
            if (acumulado <= 0.8g) {

                im.classificacao = "A";
            } else if (acumulado <= 0.95g) {
                im.classificacao = "B";
            } else {
                im.classificacao = "C";
            }
        }
    }

    private void calculaTotal() {
        for (ItemModel im : produtos) {
            totalProdutos = totalProdutos + im.getTotal();
        }
    }

    private void adicionaProdutos(List<ItemVenda> itensVenda) {
        for (ItemVenda iv : itensVenda) {
            boolean contem = false;
            for (ItemModel im : produtos) {
                if (iv.getProduto().equals(im.produto)) {
                    im.add(iv.getQuantidade());
                    contem = true;
                }
            }
            
            if (!contem) {
                ItemModel im = new ItemModel();
                im.produto = iv.getProduto();
                im.quantidade = iv.getQuantidade();
                im.valorUnitario = iv.getValorUnitario();
                produtos.add(im);
            }
        }
    }

    @Override
    public String getColumnName(int column) {
        return Colunas.values()[column].nome;
    }

    @Override
    public int getRowCount() {
        return produtos.size();
    }

    @Override
    public int getColumnCount() {
        return Colunas.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemModel im = produtos.get(rowIndex);
        switch (Colunas.values()[columnIndex]) {
            case Colunas.ID_PRODUTO:            return im.produto.id
            case Colunas.NOME:                  return im.produto.nome
            case Colunas.QUANTIDADE:            return im.quantidade;
            case Colunas.TOTAL:                 return DF.format(im.total)
            case Colunas.PORCENTAGEM:           return im.total.divide(totalProdutos, new MathContext(3))
                        .multiply(BigDecimal.valueOf(100))
            case Colunas.PORCENTAGEM_ACUMULADA: return im.porcentagemAcumulada * 100g
            case Colunas.CLASSIFICACAO:         return im.classificacao
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (produtos.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public void setJTable(JTable table) {
        table.model = this;
    }
}
