/*
 * GerenciadorDeVendas: PanelParcelamento.java
 * Enconding: UTF-8
 * Data de criação: 20/03/2019 11:37:59
 */
package gerenciadordevendas.telas;

import gerenciadordevendas.model.Venda;

/**
 *
 * @author Ramon Porto
 */
public class PanelParcelamento extends javax.swing.JPanel {

    private Venda venda;
    
    public PanelParcelamento(Venda venda) {
        initComponents();
        this.venda = venda;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNumeroParcelas = new javax.swing.JLabel();
        SpinnerIniciarEm = new javax.swing.JSpinner();
        lblIniciarEm = new javax.swing.JLabel();
        txtIniciarEm = new com.toedter.calendar.JDateChooser();
        txtIntervalo = new javax.swing.JSpinner();
        lblIntervalo = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));

        lblNumeroParcelas.setText("Número de Parcelas");

        SpinnerIniciarEm.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        lblIniciarEm.setText("Iniciar em");

        txtIniciarEm.setDate(new java.util.Date());

        txtIntervalo.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        lblIntervalo.setText("Com intervalos de");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dias", "Semanas", "Meses", "Anos" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNumeroParcelas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SpinnerIniciarEm, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblIniciarEm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIniciarEm, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblIntervalo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblIntervalo)
                        .addComponent(txtIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtIniciarEm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNumeroParcelas)
                        .addComponent(SpinnerIniciarEm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblIniciarEm)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner SpinnerIniciarEm;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel lblIniciarEm;
    private javax.swing.JLabel lblIntervalo;
    private javax.swing.JLabel lblNumeroParcelas;
    private com.toedter.calendar.JDateChooser txtIniciarEm;
    private javax.swing.JSpinner txtIntervalo;
    // End of variables declaration//GEN-END:variables
}
