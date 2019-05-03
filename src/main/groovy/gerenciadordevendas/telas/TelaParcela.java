/*
 * GerenciadorDeVendas: TelaParcela.java
 * Enconding: UTF-8
 * Data de criação: 03/04/2019 11:10:42
 */
package gerenciadordevendas.telas;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.FormaPagamento;
import java.awt.Dialog;
import gerenciadordevendas.model.Parcela;
import gerenciadordevendas.tablemodel.ParcelaTableModel;
import gerenciadordevendas.telas.listener.MoedaDocumentListener;
import gerenciadordevendas.telas.util.TelaUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramon Porto
 */
public class TelaParcela extends javax.swing.JDialog {

    private final Parcela parcela;
    private final ParcelaTableModel model;

    public TelaParcela(Parcela parcela, ParcelaTableModel model) {
        super(null, Dialog.DEFAULT_MODALITY_TYPE);
        initComponents();
        this.parcela = parcela;
        
        new MoedaDocumentListener(txtValor).inicializa();
        preencheCampos();
        this.model = model;
        if (parcela.getId() == 0) {
            btnInformarPagamento.setVisible(false);
        }
    }
    
    private void preencheCampos() {
        txtValor.setText(parcela.getValor().setScale(2, RoundingMode.HALF_UP).toString());
        EntityManager em = JPA.getEM();
        TelaUtil.carregarObjetosNaComboBox(em, cmbForma, FormaPagamento.class);
        em.close();
        cmbForma.setSelectedItem(parcela.getFormaPagamento());
        txtVencimento.setDate(parcela.getVencimento());
        txtDataPagamento.setDate(parcela.getDataPagamento());
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        txtVencimento = new com.toedter.calendar.JDateChooser();
        txtDataPagamento = new com.toedter.calendar.JDateChooser();
        btnOk = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cmbForma = new javax.swing.JComboBox<>();
        btnInformarPagamento = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Valor R$");

        jLabel2.setText("Forma de Pagamento");

        jLabel3.setText("Vencimento");

        jLabel4.setText("Data do Pagamento");

        txtDataPagamento.setEnabled(false);

        btnOk.setText("Ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnInformarPagamento.setText("Informar Pagamento");
        btnInformarPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformarPagamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbForma, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtValor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtVencimento, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(txtDataPagamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnInformarPagamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValor)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbForma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtVencimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDataPagamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnInformarPagamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        BigDecimal valor = new BigDecimal(txtValor.getText());
        Date vencimento = txtVencimento.getDate();
        Date dataPagamento = txtDataPagamento.getDate();
        FormaPagamento forma = (FormaPagamento) cmbForma.getSelectedItem();
        
        List<Parcela> parcelas = model.getVenda().getParcelas();
        if (!parcelas.isEmpty() && vencimento.before(parcelas.get(parcelas.size() -1).getVencimento()) ) {
            JOptionPane.showMessageDialog(this,"A data de vencimeto da parcela não pode ser anterior a ultima parcela");
            return;
        }
        if (model.getVenda().getTotal().subtract(model.getTotalParcelas().subtract(parcela.getValor())).compareTo(valor) < 0) {
            JOptionPane.showMessageDialog(this,"O valor da parcela não pode ser maior que o total da Venda");
            return;
        } else if (valor.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(this, "O valor da parcela precisa ser maior que ZERO");
            return;
        }
        
        parcela.setValor(valor);
        parcela.setFormaPagamento(forma);
        parcela.setVencimento(vencimento);
        parcela.setDataPagamento(dataPagamento);
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnInformarPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformarPagamentoActionPerformed
        txtDataPagamento.setDate(Calendar.getInstance().getTime());
        btnOkActionPerformed(evt);
    }//GEN-LAST:event_btnInformarPagamentoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnInformarPagamento;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<FormaPagamento> cmbForma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JDateChooser txtDataPagamento;
    private javax.swing.JTextField txtValor;
    private com.toedter.calendar.JDateChooser txtVencimento;
    // End of variables declaration//GEN-END:variables
}
