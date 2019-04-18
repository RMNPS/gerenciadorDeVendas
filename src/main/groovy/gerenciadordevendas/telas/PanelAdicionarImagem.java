/*
 * GerenciadorDeVendas: PanelAdicionarImagem.java
 * Enconding: UTF-8
 * Data de criação: 16/04/2019 09:30:46
 */
package gerenciadordevendas.telas;

import gerenciadordevendas.Regras;
import gerenciadordevendas.FilesWindowOpener;
import java.io.File;
import java.nio.file.Paths;
import javax.swing.ImageIcon;

/**
 *
 * @author Ramon Porto
 */
public class PanelAdicionarImagem extends javax.swing.JPanel {

    private String caminhoImagem;
    
    public PanelAdicionarImagem() {
        initComponents();
    }

    public void setCaminhoImagemEntidade(String caminhoImagem) {
        if (caminhoImagem != null) {
            String caminho = Paths.get("").toAbsolutePath().toString();
            File imagem = new File(caminho + caminhoImagem);
            if (imagem.exists()) {
                this.caminhoImagem = imagem.getAbsolutePath();
                preencheImagem();
                return;
            }
        }
        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/baseline_photo_black_24dp.png")));
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFoto = new javax.swing.JLabel();
        btnAdicionarFoto = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(btnAdicionarFoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton5)
                    .addComponent(btnAdicionarFoto))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarFotoActionPerformed
        File caminho = FilesWindowOpener.getCaminhoSelecionarImagem(Regras.ULTIMO_CAMINHO_IMAGEM);
        if (caminho == null) {
            caminhoImagem = null;
            return;
        }
        caminhoImagem = caminho.getAbsolutePath();
        preencheImagem();
    }//GEN-LAST:event_btnAdicionarFotoActionPerformed

    private void preencheImagem() {

        Regras.ULTIMO_CAMINHO_IMAGEM = caminhoImagem;
        ImageIcon icon = new ImageIcon(caminhoImagem);
        icon.setImage(icon.getImage().getScaledInstance(332, 257, 100));
        lblFoto.setIcon(icon);
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        caminhoImagem = null;
        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/baseline_photo_black_24dp.png")));
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarFoto;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel lblFoto;
    // End of variables declaration//GEN-END:variables
}
