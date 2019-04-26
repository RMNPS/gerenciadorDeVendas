package gerenciadordevendas.telas;

import gerenciadordevendas.tablemodel.TableModelPesquisavel;
import gerenciadordevendas.telas.listener.PesquisarListener;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Optional;
import javax.swing.SwingUtilities;

public class TelaPesquisar extends javax.swing.JDialog {

    private TableModelPesquisavel model = null;
    private Object objetoSelecionado = null;
    private boolean objetoObtido = false;
    private boolean popupVisible = true;
    private boolean editarBloqueado = false;

    public TelaPesquisar(TableModelPesquisavel model) {
        this(model, true);
    }

    public TelaPesquisar(TableModelPesquisavel model, boolean selecionavel) {
        super(null, java.awt.Dialog.DEFAULT_MODALITY_TYPE);
        this.model = model;
        initComponents();
        model.setJTable(tabela);
        model.atualizaEspacamentoColunas();
        if (model.getRowCount() > 0) {
            tabela.setRowSelectionInterval(0, 0);
        }
        campoTextField.getDocument().addDocumentListener(new PesquisarListener(model));

        selecionarMenu.setVisible(selecionavel);
    }

    public Optional getItemSelecionado() {
        setVisible(true);
        if (objetoObtido == false) {
            objetoObtido = true;
        } else {
            return Optional.empty();
        }
        return Optional.ofNullable(objetoSelecionado);
    }
    
    public TelaPesquisar setPopupVisible(boolean enable){
        popupVisible = enable;
        return this;
    }
    
    public void bloquearEditar(boolean b){
        editarBloqueado = b;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        selecionarMenu = new javax.swing.JMenuItem();
        editarMenu = new javax.swing.JMenuItem();
        novoMenu = new javax.swing.JMenuItem();
        removerMenu = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        campoTextField = new javax.swing.JTextField();

        selecionarMenu.setText("Selecionar");
        selecionarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecionarMenuActionPerformed(evt);
            }
        });
        popupMenu.add(selecionarMenu);

        editarMenu.setText("Editar");
        editarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarMenuActionPerformed(evt);
            }
        });
        popupMenu.add(editarMenu);

        novoMenu.setText("Novo");
        novoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                novoMenuActionPerformed(evt);
            }
        });
        popupMenu.add(novoMenu);

        removerMenu.setText("Remover");
        removerMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerMenuActionPerformed(evt);
            }
        });
        popupMenu.add(removerMenu);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisar");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/briefcase-outline.png")));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseReleased(evt);
            }
        });

        tabela.setAutoCreateRowSorter(true);
        tabela.setModel(model);
        tabela.setShowHorizontalLines(false);
        tabela.setShowVerticalLines(false);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        tabela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);

        campoTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoTextFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(campoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void campoTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoTextFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (selecionarMenu.isVisible()) {
                defineObjetoSelecionado();
            } else {
                int row = tabela.getSelectedRow();
                if (row > -1) {
                    model.editar(this);
                }
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_F2 || evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (model.getRowCount() > 1) {
                tabela.setRowSelectionInterval(1, 1);
            }
            tabela.requestFocus();
        }
    }//GEN-LAST:event_campoTextFieldKeyPressed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        SwingUtilities.invokeLater(() -> {
            int linha = tabela.rowAtPoint(evt.getPoint());
            if (linha >= 0 && linha < tabela.getRowCount()) {
                tabela.setRowSelectionInterval(linha, linha);
            }
            if (SwingUtilities.isRightMouseButton(evt) && popupVisible) {
                
                selecionarMenu.setEnabled(true);
                editarMenu.setEnabled(!editarBloqueado);
                removerMenu.setEnabled(true);
                popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            } else if (evt.getClickCount() == 2) {
                if (selecionarMenu.isVisible()) {
                    defineObjetoSelecionado();
                } else {
                    int row = tabela.getSelectedRow();
                    if (row > -1) {
                        model.editar(this);
                    }
                }
            }
        });
    }//GEN-LAST:event_tabelaMouseClicked

    private void defineObjetoSelecionado() {
        int row = tabela.getSelectedRow();
        if (row > -1) {
            objetoSelecionado = model.getObjetoLinha(row);
            dispose();
        }
    }

    private void tabelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (selecionarMenu.isVisible()) {
                defineObjetoSelecionado();
            } else {
                int row = tabela.getSelectedRow();
                if (row > -1) {
                    model.editar(this);
                }
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_UP && tabela.getSelectedRow() == 0) {
            campoTextField.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_F2 || evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        }
    }//GEN-LAST:event_tabelaKeyPressed

    private void selecionarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecionarMenuActionPerformed
        defineObjetoSelecionado();
    }//GEN-LAST:event_selecionarMenuActionPerformed

    private void editarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarMenuActionPerformed
        int row = tabela.getSelectedRow();
        if (row > -1) {
            model.editar(this);
        }
    }//GEN-LAST:event_editarMenuActionPerformed

    private void novoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_novoMenuActionPerformed
        model.novo(this);
    }//GEN-LAST:event_novoMenuActionPerformed

    private void jScrollPane1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseReleased
        if (SwingUtilities.isRightMouseButton(evt) && popupVisible) {
            selecionarMenu.setEnabled(false);
            editarMenu.setEnabled(false);
            removerMenu.setEnabled(false);
            popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jScrollPane1MouseReleased

    private void removerMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerMenuActionPerformed
        int row = tabela.getSelectedRow();
        if (row > -1) {
            model.remover(this);
        }
    }//GEN-LAST:event_removerMenuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField campoTextField;
    private javax.swing.JMenuItem editarMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem novoMenu;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem removerMenu;
    private javax.swing.JMenuItem selecionarMenu;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
