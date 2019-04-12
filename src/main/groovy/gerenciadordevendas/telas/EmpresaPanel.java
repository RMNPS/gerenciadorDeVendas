/*
 * GerenciadorDeVendas: EmpresaPanel.java
 * Enconding: UTF-8
 * Data de criação: 11/04/2019 10:45:21
 */
package gerenciadordevendas.telas;

import com.google.common.io.Files;
import gerenciadordevendas.FilesWindowOpener;
import gerenciadordevendas.JPA;
import gerenciadordevendas.Regras;
import gerenciadordevendas.formatador.Formatador;
import gerenciadordevendas.model.Empresa;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramon Porto
 */
public class EmpresaPanel extends javax.swing.JPanel {

    private boolean deletarFoto = false;
    private Empresa empresa;
    
    /** Creates new form EmpresaPanel */
    public EmpresaPanel() {
        initComponents();
    }

    public void preencherCampos() {
        txtNome.setText(empresa.getNome());
        txtEmail.setText(empresa.getEmail());
        txtCNPJ.setText(empresa.getCnpj());
        txtInscricaoEstadual.setText(empresa.getInscricaoEstadual());
        txtInscricaoMunicipal.setText(empresa.getInscricaoMunicipal());
        txtTelefone.setText(empresa.getTelefone());
        txtCelular.setText(empresa.getCelular());
    }
    
    public void validar() {
        empresa.setNome(Formatador.stringNotEmpty(lblNome.getText(), txtNome.getText()));
        empresa.setEmail(txtEmail.getText());
        empresa.setCnpj(txtCNPJ.getText());
        empresa.setInscricaoEstadual(txtInscricaoEstadual.getText());
        empresa.setInscricaoMunicipal(txtInscricaoMunicipal.getText());
        empresa.setTelefone(txtTelefone.getText());
        empresa.setCelular(txtCelular.getText());
        if (deletarFoto && empresa.getCaminhoLogotipo()!= null) {
            
            String caminho = Paths.get("").toAbsolutePath().toString();
            try {
                java.nio.file.Files.deleteIfExists(Paths.get(caminho + empresa.getCaminhoLogotipo()));
            } catch (IOException ex) {
                Logger.getLogger(EmpresaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            empresa.setCaminhoLogotipo(null);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDadosGerais = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblNome1 = new javax.swing.JLabel();
        txtCNPJ = new javax.swing.JFormattedTextField();
        lvlInscricaoEstadual = new javax.swing.JLabel();
        txtInscricaoEstadual = new javax.swing.JTextField();
        lvlInscricaoEstadual1 = new javax.swing.JLabel();
        txtInscricaoMunicipal = new javax.swing.JTextField();
        lvlInscricaoEstadual2 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JTextField();
        txtCelular = new javax.swing.JTextField();
        lvlInscricaoEstadual3 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        lblFoto = new javax.swing.JLabel();
        btnAdicionarFoto = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        panelDadosGerais.setBackground(new java.awt.Color(255, 255, 255));

        lblNome.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblNome.setText("Nome");

        lblNome1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblNome1.setText("CNPJ");

        try {
            txtCNPJ.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lvlInscricaoEstadual.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lvlInscricaoEstadual.setText("Inscrição Estadual");

        lvlInscricaoEstadual1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lvlInscricaoEstadual1.setText("Inscrição Municipal");

        lvlInscricaoEstadual2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lvlInscricaoEstadual2.setText("Telefone");

        txtTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefoneActionPerformed(evt);
            }
        });

        lvlInscricaoEstadual3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lvlInscricaoEstadual3.setText("Celular");

        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblEmail.setText("E-mail");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/baseline_photo_black_24dp.png"))); // NOI18N
        lblFoto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnAdicionarFoto.setText("Adicionar");
        btnAdicionarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarFotoActionPerformed(evt);
            }
        });

        jButton5.setText("Remover");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(btnAdicionarFoto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionarFoto)
                    .addComponent(jButton5))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelDadosGeraisLayout = new javax.swing.GroupLayout(panelDadosGerais);
        panelDadosGerais.setLayout(panelDadosGeraisLayout);
        panelDadosGeraisLayout.setHorizontalGroup(
            panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDadosGeraisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lvlInscricaoEstadual, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNome1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDadosGeraisLayout.createSequentialGroup()
                        .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lvlInscricaoEstadual1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lvlInscricaoEstadual2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lvlInscricaoEstadual3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1))
                    .addComponent(lblEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                    .addComponent(lblNome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDadosGeraisLayout.createSequentialGroup()
                        .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtInscricaoEstadual, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtInscricaoMunicipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelular, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNome))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        panelDadosGeraisLayout.setVerticalGroup(
            panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDadosGeraisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDadosGeraisLayout.createSequentialGroup()
                        .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome1)
                            .addComponent(txtCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lvlInscricaoEstadual)
                            .addComponent(txtInscricaoEstadual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lvlInscricaoEstadual1)
                            .addComponent(txtInscricaoMunicipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lvlInscricaoEstadual2)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lvlInscricaoEstadual3))
                        .addGap(0, 151, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDadosGerais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDadosGerais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        deletarFoto = true;
        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/baseline_photo_black_24dp.png")));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnAdicionarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarFotoActionPerformed
        File caminhoImagem = FilesWindowOpener.getCaminhoSelecionarImagem(Regras.ULTIMO_CAMINHO_IMAGEM);
        if (caminhoImagem == null) {
            return;
        }
        Regras.ULTIMO_CAMINHO_IMAGEM = caminhoImagem.getAbsolutePath();
        ImageIcon icon = new ImageIcon(caminhoImagem.getAbsolutePath());
        icon.setImage(icon.getImage().getScaledInstance(332, 257, 100));

        String caminho = Paths.get("").toAbsolutePath().toString();

        try {
            if (!new File(caminho + "/logos_empresas/").exists()) {
                new File(caminho + "/logos_empresas/").mkdir();
            }
            String fileExtension = Files.getFileExtension(caminhoImagem.getPath());

            int id = empresa.getId();
            if (id == 0) {
                EntityManager em = JPA.getEM();
                id = (int) em.createQuery("select t from Empresa t order by t.id desc").setMaxResults(1).getResultStream().findAny().orElse(0);
                id++;
            }
            
            Files.copy(caminhoImagem, new File(caminho + "/logos_empresas/" + id + fileExtension));
            empresa.setCaminhoLogotipo("/logos_empresas/" + + id + fileExtension);
            lblFoto.setIcon(icon);
            deletarFoto = false;
        } catch (IOException ex) {
            Logger.getLogger(TelaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAdicionarFotoActionPerformed

    private void txtTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefoneActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarFoto;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNome1;
    private javax.swing.JLabel lvlInscricaoEstadual;
    private javax.swing.JLabel lvlInscricaoEstadual1;
    private javax.swing.JLabel lvlInscricaoEstadual2;
    private javax.swing.JLabel lvlInscricaoEstadual3;
    private javax.swing.JPanel panelDadosGerais;
    private javax.swing.JFormattedTextField txtCNPJ;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtInscricaoEstadual;
    private javax.swing.JTextField txtInscricaoMunicipal;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
