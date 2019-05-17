    package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA;
import gerenciadordevendas.exception.TransacaoException
import gerenciadordevendas.model.ItemEstoque
import gerenciadordevendas.model.Parcela
import gerenciadordevendas.model.FormaPagamento
import gerenciadordevendas.model.Venda
import gerenciadordevendas.telas.TelaItemEstoque
import gerenciadordevendas.telas.TelaParcela

    import javax.persistence.EntityManager
    import javax.persistence.Query
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

    ParcelaTableModel(JTable tabela, Venda venda) {
        super(["Sequência", "Forma de Pagamento", 'Valor R$', "Vencimento", 'Estado'])
        setVenda(venda)
        setJTable(tabela)
    }

    
    void add(Parcela parcela) throws TransacaoException {
        if (venda.parcelas && parcela.vencimento < venda.parcelas.get(venda.parcelas.size() -1).vencimento) {
            throw new TransacaoException ("A data de vencimento da parcela não pode ser anterior à última parcela")
        }
        if (saldo - parcela.valor < 0g) {
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
        this.venda = venda
        if (!venda.parcelas) {
            this.venda.parcelas = new ArrayList<Parcela>()
        }
        carregar();
    }
    
    BigDecimal getTotalParcelas() {
        BigDecimal totalParcelas = 0g;
        for (Parcela parcela: venda.parcelas) {
            totalParcelas += parcela.valor;
        }
        return totalParcelas
    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(5, 35, 20, 20, 20)
    }

    void carregar() {
        if (venda) {
            dadosBackup = dados = venda.parcelas ?: []
            fireTableDataChanged()
        }
    }
    
    @Override
    void editar(Window parent) {
        int row = getJTable().getSelectedRow()
        if (row > -1){
            new TelaParcela(venda.parcelas.get(row), this).setVisible(true)
            carregar()
        }
    }
    
    @Override
    void remover(java.awt.Window parent) {
        int row = getJTable().getSelectedRow()
        if (row > -1) {
            Parcela parcela = venda.parcelas.get(row)
            parcela.deleted = true
            venda.parcelas.remove(parcela)
            carregar()
        }
    }

    @Override
    protected Query getQuery(EntityManager em) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    protected boolean getSeachFilter(Parcela parcela, String campoLowerCase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        Parcela parcela = get(rowIndex);
        switch (colunas[columnIndex]) {
            case "Sequência":           return rowIndex
            case "Forma de Pagamento":  return parcela.formaPagamento.descricao
            case 'Valor R$':            return df.format(parcela.valor)
            case "Vencimento":          return parcela.vencimento
            case 'Estado':              return parcela.dataPagamento ? "Paga" : "Em Negociação"
            default:                    return null;
        }
    }
}
