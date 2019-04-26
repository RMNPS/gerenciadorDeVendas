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
        java.awt.GridBagConstraints gridBagConstraints;

        vendasButton = new javax.swing.JButton();
        fornecedoresButton = new javax.swing.JButton();
        estoqueButton = new javax.swing.JButton();
        clientesButton2 = new javax.swing.JButton();
        configButton = new javax.swing.JButton();
        dadosButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerenciador De Vendas");
        setExtendedState(6);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/briefcase-outline.png")));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        vendasButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_shopping_cart_black_24dp_2x.png"))); // NOI18N
        vendasButton.setText("PDV");
        vendasButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        vendasButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        vendasButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendasButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(86, 170, 0, 0);
        getContentPane().add(vendasButton, gridBagConstraints);

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(41, 170, 137, 0);
        getContentPane().add(fornecedoresButton, gridBagConstraints);

        estoqueButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_dashboard_black_24dp_2x.png"))); // NOI18N
        estoqueButton.setText("Estoque");
        estoqueButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        estoqueButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        estoqueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estoqueButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(86, 80, 0, 0);
        getContentPane().add(estoqueButton, gridBagConstraints);

        clientesButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_person_black_24dp_2x.png"))); // NOI18N
        clientesButton2.setText("Clientes");
        clientesButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clientesButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        clientesButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientesButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(86, 78, 0, 0);
        getContentPane().add(clientesButton2, gridBagConstraints);

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(41, 78, 137, 169);
        getContentPane().add(configButton, gridBagConstraints);

        dadosButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_timeline_black_24dp_2x.png"))); // NOI18N
        dadosButton.setText("Dados");
        dadosButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dadosButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        dadosButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dadosButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(41, 80, 137, 0);
        getContentPane().add(dadosButton, gridBagConstraints);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void vendasButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendasButtonActionPerformed
        TelaPDV telaVendas = new TelaPDV();
        
        telaVendas.setVisible(true);
    }//GEN-LAST:event_vendasButtonActionPerformed

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
    private javax.swing.JButton clientesButton2;
    private javax.swing.JButton configButton;
    private javax.swing.JButton dadosButton;
    private javax.swing.JButton estoqueButton;
    private javax.swing.JButton fornecedoresButton;
    private javax.swing.JButton vendasButton;
    // End of variables declaration//GEN-END:variables
}
