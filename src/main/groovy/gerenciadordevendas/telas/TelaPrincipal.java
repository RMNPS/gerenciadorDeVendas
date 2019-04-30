package gerenciadordevendas.telas;

import gerenciadordevendas.InicializadorBaseDeDados;
import gerenciadordevendas.JPA;
import gerenciadordevendas.Regras;
import gerenciadordevendas.telas.util.DataCreator;
import gerenciadordevendas.model.Cliente;
import gerenciadordevendas.model.Empresa;
import gerenciadordevendas.model.TipoEmpresa;
import gerenciadordevendas.model.Vendedor;
import gerenciadordevendas.tablemodel.ClienteTableModel;
import gerenciadordevendas.tablemodel.FornecedorTableModel;
import gerenciadordevendas.tablemodel.VendaTableModel;
import java.awt.Toolkit;
import java.util.Optional;
import javax.persistence.EntityManager;

public class TelaPrincipal extends javax.swing.JFrame {

    TelaEstoque telaEstoque;
    
    public TelaPrincipal() {
        new InicializadorBaseDeDados().incializar();
        initComponents();
        new DataCreator().create();
        Regras.load();
        
        EntityManager em = JPA.getEM();
        Cliente clientePadrao = em.find(Cliente.class, Cliente.padrao().getId());
        Vendedor operadorPadrao = em.find(Vendedor.class, Vendedor.padrao().getId());
        if (clientePadrao == null) {
            em.getTransaction().begin();
            em.merge(Cliente.padrao());
            em.merge(Vendedor.padrao());
            em.getTransaction().commit();
        }
//        Optional matriz = em.createQuery("SELECT e FROM Empresa e WHERE e.tipoEmpresa = :x")
//                .setParameter("x", TipoEmpresa.MATRIZ)
//                .getResultStream().findAny();
//        if (!matriz.isPresent()) {
//            Empresa empresa = new Empresa();
//            empresa.setTipoEmpresa(TipoEmpresa.MATRIZ);
//            TelaEmpresa telaEmpresa = new TelaEmpresa();
//            telaEmpresa.setEmpresa(empresa);
//            telaEmpresa.setVisible(true);
//        }
        em.close();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnPDV = new javax.swing.JButton();
        fornecedoresButton = new javax.swing.JButton();
        estoqueButton = new javax.swing.JButton();
        clientesButton2 = new javax.swing.JButton();
        configButton = new javax.swing.JButton();
        dadosButton = new javax.swing.JButton();
        btnVendas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerenciador De Vendas");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/briefcase-outline.png")));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btnPDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_shopping_cart_black_24dp_2x.png"))); // NOI18N
        btnPDV.setText("PDV");
        btnPDV.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPDV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDVActionPerformed(evt);
            }
        });

        fornecedoresButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_person_outline_black_24dp_2x.png"))); // NOI18N
        fornecedoresButton.setText("Fornecedores");
        fornecedoresButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fornecedoresButton.setMaximumSize(new java.awt.Dimension(81, 75));
        fornecedoresButton.setMinimumSize(new java.awt.Dimension(81, 75));
        fornecedoresButton.setPreferredSize(new java.awt.Dimension(81, 75));
        fornecedoresButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fornecedoresButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fornecedoresButtonActionPerformed(evt);
            }
        });

        estoqueButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_dashboard_black_24dp_2x.png"))); // NOI18N
        estoqueButton.setText("Estoque");
        estoqueButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        estoqueButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        estoqueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estoqueButtonActionPerformed(evt);
            }
        });

        clientesButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_person_black_24dp_2x.png"))); // NOI18N
        clientesButton2.setText("Clientes");
        clientesButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clientesButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        clientesButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientesButton2ActionPerformed(evt);
            }
        });

        configButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_settings_black_24dp_2x.png"))); // NOI18N
        configButton.setText("Configurações");
        configButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        configButton.setMaximumSize(new java.awt.Dimension(81, 75));
        configButton.setMinimumSize(new java.awt.Dimension(81, 75));
        configButton.setPreferredSize(new java.awt.Dimension(81, 75));
        configButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        configButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configButtonActionPerformed(evt);
            }
        });

        dadosButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_timeline_black_24dp_2x.png"))); // NOI18N
        dadosButton.setText("Dados");
        dadosButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dadosButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        dadosButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dadosButtonActionPerformed(evt);
            }
        });

        btnVendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_shopping_cart_black_24dp_2x.png"))); // NOI18N
        btnVendas.setText("Vendas");
        btnVendas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVendas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVendasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dadosButton, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(fornecedoresButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPDV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnVendas, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(configButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientesButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(estoqueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPDV, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(estoqueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fornecedoresButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clientesButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dadosButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(configButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDVActionPerformed
        TelaPDV telaVendas = new TelaPDV();
        
        telaVendas.setVisible(true);
    }//GEN-LAST:event_btnPDVActionPerformed

    private void fornecedoresButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fornecedoresButtonActionPerformed
        new TelaPesquisar(new FornecedorTableModel(), false).setVisible(true);
    }//GEN-LAST:event_fornecedoresButtonActionPerformed

    private void estoqueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estoqueButtonActionPerformed
//        new TelaPesquisar(new ItemEstoqueTableModel(), false).setVisible(true);
        if (telaEstoque == null || telaEstoque.isFechada()) {
            telaEstoque = new TelaEstoque();
            telaEstoque.setVisible(true);
        } else {
            telaEstoque.requestFocus();
        }
        
    }//GEN-LAST:event_estoqueButtonActionPerformed

    private void clientesButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientesButton2ActionPerformed
        new TelaPesquisar(new ClienteTableModel(), false).setVisible(true);
    }//GEN-LAST:event_clientesButton2ActionPerformed

    private void configButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configButtonActionPerformed
        new TelaConfiguracoes().setVisible(true);
    }//GEN-LAST:event_configButtonActionPerformed

    private void dadosButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dadosButtonActionPerformed
        new TelaDados().setVisible(true);
    }//GEN-LAST:event_dadosButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        EntityManager em = JPA.getEM();
        Optional matriz = em.createQuery("SELECT e FROM Empresa e WHERE e.tipoEmpresa = :x")
                .setParameter("x", TipoEmpresa.MATRIZ)
                .getResultStream().findAny();
        em.close();
        if (!matriz.isPresent()) {
            Empresa empresa = new Empresa();
            empresa.setTipoEmpresa(TipoEmpresa.MATRIZ);
            TelaEmpresa telaEmpresa = new TelaEmpresa(this, "Informe os dados da Matriz da sua Empresa");
            telaEmpresa.setEmpresa(empresa);
            telaEmpresa.setVisible(true);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVendasActionPerformed
        new TelaPesquisar(new VendaTableModel(), false).setVisible(true);
    }//GEN-LAST:event_btnVendasActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("GTK+".equals(info.getName()) || "Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPDV;
    private javax.swing.JButton btnVendas;
    private javax.swing.JButton clientesButton2;
    private javax.swing.JButton configButton;
    private javax.swing.JButton dadosButton;
    private javax.swing.JButton estoqueButton;
    private javax.swing.JButton fornecedoresButton;
    // End of variables declaration//GEN-END:variables
}
