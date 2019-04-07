package gerenciadordevendas.tablemodel;

import gerenciadordevendas.exception.TransacaoException
import gerenciadordevendas.JPA
import gerenciadordevendas.model.Conta
import gerenciadordevendas.model.RegistroDeFluxo
import gerenciadordevendas.model.Venda
import gerenciadordevendas.telas.TelaPesquisar
import java.awt.Window
import java.awt.Color
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import javax.persistence.EntityManager
import javax.swing.JTable
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.table.DefaultTableCellRenderer
import java.awt.Component


class ContaTableModel extends TableModelPesquisavel {

    static final DecimalFormat DF = new DecimalFormat('R$ #,##0.00')
    static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm")

    final List<RegistroDeFluxo> transacoes = new ArrayList<>()
    JTable tabela
    Conta conta

    enum Colunas {
        ID("id"), TIPO("Tipo de Transação"), DATA("Data"), VALOR('Valor R$'), ESTADO("Estado");

        String nome;

        Colunas(String nome) {
            this.nome = nome;
        }
    }

    ContaTableModel(Conta conta) {
        this.conta = conta;
        atualizar(conta);
    }

    def atualizar(Conta conta) {
        this.conta = conta;
        transacoes.clear();
        transacoes.addAll(conta.pagamentos);
        transacoes.addAll(conta.vendas);
        transacoes.addAll(conta.descontos);
        transacoes.addAll(conta.juros);
        transacoes.addAll(conta.estadosAsRegistroDeFluxo);
        //        Collections.reverse(transacoes);
        transacoes.sort(Collections.reverseOrder());
        fireTableDataChanged();
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        RegistroDeFluxo registro = transacoes.get(rowIndex);
        switch (Colunas.values()[columnIndex]) {
            case Colunas.ID:        return registro.id
            case Colunas.TIPO:      return registro.tipo
            case Colunas.DATA:      return SDF.format(registro.getDataCriacao())
            case Colunas.VALOR:     return registro.getTotal() == null? "" : DF.format(registro.getTotal())
            case Colunas.ESTADO:    return (registro instanceof Venda) ? ((Venda) registro).estado.toString() : ""
        }
        return null;
    }

    @Override
    String getColumnName(int column) {
        return Colunas.values()[column].nome;
    }

    @Override
    Class<?> getColumnClass(int columnIndex) {
        if (transacoes.isEmpty() || columnIndex == 0) {
            return Object.class;
        }
        if (getValueAt(0, columnIndex) == null) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    void setJTable(JTable tabela) {
        this.tabela = tabela
        tabela.model = this
        setJTableColumnsWidth(tabela, 800, 5, 50, 20, 15, 10)
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                int getHorizontalAlignment() {
                    return JLabel.CENTER
                }

                @Override
                Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (isSelected) {
                        c.background = table.selectionBackground
                        c.foreground = Color.WHITE
                    } else {
                        transacoes.get(row).paintCell(c)
                    }
                    return c
                }
            });
    }

    @Override
    void editar(Window parent) {
        int row = tabela.selectedRow;
        if (row > -1) {
            RegistroDeFluxo registro = transacoes.get(row)
            if (registro instanceof Venda)
                new TelaPesquisar(new VendaTableModel((Venda) registro), false)
                    .setPopupVisible(false)
                    .setVisible(true)
            else JOptionPane.showMessageDialog(parent, "Esta opção só está habilitada para Vendas")
        }
    }

    @Override
    void remover(Window parent) {
        int row = tabela.selectedRow
        if (row > -1) {
            RegistroDeFluxo transacao = transacoes.get(row)
            EntityManager em = JPA.getEM()
            em.transaction.begin()
            try {
                conta.remover(transacao)
                em.merge(transacao)
                
            } catch (TransacaoException ex) {
                JOptionPane.showMessageDialog(parent, ex.message);
            }
            em.transaction.commit();
            em.close();
            atualizar(conta);
        }
    }

    @Override
    int getRowCount() {
        transacoes.size()
    }
    
    @Override
    int getColumnCount() {
        Colunas.values().length
    }
}


