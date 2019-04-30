    package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA;
import gerenciadordevendas.exception.TransacaoException
import gerenciadordevendas.model.ItemEstoque
import gerenciadordevendas.model.Parcela
import gerenciadordevendas.model.FormaPagamento
import gerenciadordevendas.model.Venda
import gerenciadordevendas.telas.TelaItemEstoque
import gerenciadordevendas.telas.TelaParcela
import java.awt.Window
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.List
import java.util.ArrayList
import javax.swing.JOptionPane;

import javax.swing.JTable

class ParcelaTableModel extends AbstractTableModelPesquisavel<Parcela> {

    final DecimalFormat df = new DecimalFormat('R$ #,##0.00');
    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Venda venda;
    
    enum Colunas {
        SEQUENCIA("Sequencia"), FORMA("Forma de Pagamento"), VALOR('Valor R$'), VENCIMENTO("Vencimento");

        String nome;

        Colunas(String nome) {
            this.nome = nome;
        }
    }

    ParcelaTableModel(JTable tabela, Venda venda) {
        setVenda(venda)
        setJTable(tabela)
    }
    
    void add(Parcela parcela) throws TransacaoException {
        if (!venda.parcelas.isEmpty() && parcela.vencimento < venda.parcelas.get(venda.parcelas.size() -1).vencimento) {
            throw new TransacaoException ("A data de vencimento da parcela não pode ser anterior à última parcela")
        }
        if (getSaldo() - parcela.valor < 0g) {
            throw new TransacaoException ("O valor da parcela não pode ser maior que o total da Venda")
        } else if (parcela.valor == 0g) {
            throw new TransacaoException ("O valor da parcela precisa ser maior que ZERO")
        }
        if (!venda.parcelas.contains(parcela)) {
            parcela.venda = venda;
            venda.parcelas.add(parcela)
            carregar()
        }
    }
    
    BigDecimal getSaldo() {
        venda.total - getTotalParcelas();
    }
    
    void atualizaForma(FormaPagamento forma) {
        for (Parcela parcela: venda.parcelas) {
            parcela.formaPagamento = forma;
        }
        carregar()
    }
    
    void setVenda(Venda venda) {
        this.venda = venda;
        if (venda.parcelas == null || venda.parcelas.isEmpty()) {
            this.venda.parcelas = new ArrayList<Parcela>()
        }
        carregar();
    }
    
    BigDecimal getTotalParcelas() {
        BigDecimal totalParcelas = 0g;
        for (Parcela parcela: venda.parcelas) {
            totalParcelas += parcela.valor;
        }
        return totalParcelas;
    }

    @Override
    final void setJTable(JTable table) {
        super.table = table;
        table.model = this;
        setJTableColumnsWidth(8, 10, 40, 40);
    }

    void carregar() {
        dadosBackup = dados = venda.parcelas;
        fireTableDataChanged();
    }
    
    @Override
    void editar(Window parent) {
        int row = getJTable().getSelectedRow();
        if (row > -1){
            new TelaParcela(venda.parcelas.get(row), this).setVisible(true);
            carregar();
        }
    }
    
    @Override
    void remover(java.awt.Window parent) {
        int row = getJTable().getSelectedRow()
        if (row > -1) {
            Parcela parcela = venda.parcelas.get(row)
            parcela.deleted = true
            saldo = getTotalParcelas()
            venda.parcelas.remove(parcela)
            carregar()
        }
    }

    
    String getJPQL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    protected boolean getSeachFilter(Parcela parcela, String campoLowerCase) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        Parcela parcela = get(rowIndex);
        switch (Colunas.values()[columnIndex]) {
            case Colunas.SEQUENCIA:     return rowIndex
            case Colunas.FORMA:         return parcela.formaPagamento.descricao
            case Colunas.VALOR:         return df.format(parcela.valor)
            case Colunas.VENCIMENTO:    return parcela.vencimento
            default:                    return null;
        }
    }
    

}
