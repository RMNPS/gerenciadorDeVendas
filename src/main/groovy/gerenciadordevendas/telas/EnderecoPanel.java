/*
 * GerenciadorDeVendas: EnderecoPanel.java
 * Enconding: UTF-8
 * Data de criação: 11/04/2019 11:01:31
 */
package gerenciadordevendas.telas;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.Endereco;
import gerenciadordevendas.model.UF;
import gerenciadordevendas.telas.listener.NumeroListener;
import gerenciadordevendas.telas.listener.NumeroTextoListener;
import gerenciadordevendas.telas.listener.TextoListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Ramon Porto
 */
public class EnderecoPanel extends javax.swing.JPanel {

    private List<Endereco> enderecos = new ArrayList<>();
    private Endereco enderecoAtual;

    public EnderecoPanel() {
        initComponents();
        cmbEnderecos.setVisible(false);
        lblEnderecos.setVisible(false);
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
        this.enderecoAtual = enderecos.isEmpty() ? null : enderecos.get(0);
        
        inicializar();
    }

    public List<Endereco> getEnderecos() {
        validar();
        return enderecos;
    }

    private void inicializar() {
        
        addDocumentListener(txtLogradouro, new NumeroTextoListener(100));
        addDocumentListener(txtLogradouro, new NumeroListener(5));
        addDocumentListener(bairroTextField, new TextoListener(20));
        addDocumentListener(cidadeTextField, new TextoListener(40));

        preencheCampos();
    }

    public EnderecoPanel liberarAdicionar() {
        btnAdicionarEndereco.setVisible(true);
        return this;
    }

    public void validar() {
        enderecoAtual.setLogradouro(txtLogradouro.getText());
        enderecoAtual.setBairro(bairroTextField.getText());
        enderecoAtual.setNumero(Integer.valueOf(txtNumero.getText()));
        enderecoAtual.setCidade(cidadeTextField.getText());
        enderecoAtual.setCep(txtCep.getText());
        enderecoAtual.setUf((UF) ufComboBox.getSelectedItem());
    }

    private void preencheCampos() {
        if (enderecoAtual == null) {
            enderecoAtual = new Endereco();
            enderecos.add(enderecoAtual);
        }
        txtLogradouro.setText(enderecoAtual.getLogradouro());
        txtNumero.setText("" + enderecoAtual.getNumero());
        txtComplemento.setText(enderecoAtual.getComplemento());
        cidadeTextField.setText(enderecoAtual.getCidade());
        bairroTextField.setText(enderecoAtual.getBairro());
        txtCep.setText(enderecoAtual.getCep());
        ufComboBox.setSelectedItem(Optional.ofNullable(enderecoAtual.getUf()).orElse(UF.SC));

        cmbEnderecos.removeAllItems();
        Collections.sort(enderecos);
        for (Endereco endereco : enderecos) {
            cmbEnderecos.addItem(endereco);
        }
        if (enderecos.size() > 1) {
            cmbEnderecos.setEnabled(true);
        }
    }

    private void addDocumentListener(JTextField field, DocumentListener listener) {
        field.getDocument().addDocumentListener(listener);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbEnderecos = new javax.swing.JComboBox<>();
        lblEnderecos = new javax.swing.JLabel();
        btnAdicionarEndereco = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        lblCidade = new javax.swing.JLabel();
        txtComplemento = new javax.swing.JTextField();
        txtLogradouro = new javax.swing.JTextField();
        ufComboBox = new javax.swing.JComboBox<>();
        bairroTextField = new javax.swing.JTextField();
        lblComplemento = new javax.swing.JLabel();
        lblNumero = new javax.swing.JLabel();
        lblLogradouro = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cidadeTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCep = new javax.swing.JFormattedTextField();
        btnRemoverEndereco = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        cmbEnderecos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEnderecosItemStateChanged(evt);
            }
        });

        lblEnderecos.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblEnderecos.setText("Endereço");

        btnAdicionarEndereco.setText("Adicionar outro Endereco");
        btnAdicionarEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarEnderecoActionPerformed(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("CEP");

        lblCidade.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblCidade.setText("Cidade");

        ufComboBox.setModel(new DefaultComboBoxModel(UF.values()));

        lblComplemento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblComplemento.setText("Complemento");

        lblNumero.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumero.setText("Número");

        lblLogradouro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLogradouro.setText("Logradouro");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Bairro");

        cidadeTextField.setText("Passo de Torres");

        jLabel9.setText("UF");

        try {
            txtCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCep.setText("88980-000");

        btnRemoverEndereco.setText("Remover Endereço");
        btnRemoverEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverEnderecoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lblNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblComplemento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtComplemento))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblEnderecos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLogradouro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bairroTextField)
                            .addComponent(cidadeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ufComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtLogradouro, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbEnderecos, javax.swing.GroupLayout.Alignment.LEADING, 0, 339, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAdicionarEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                            .addComponent(btnRemoverEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEnderecos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEnderecos)
                    .addComponent(btnAdicionarEndereco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogradouro)
                    .addComponent(txtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoverEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComplemento)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumero)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bairroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ufComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCidade)
                    .addComponent(cidadeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarEnderecoActionPerformed
        cmbEnderecos.setEnabled(true);
        validar();
        enderecoAtual = null;
        preencheCampos();
    }//GEN-LAST:event_btnAdicionarEnderecoActionPerformed

    private void cmbEnderecosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEnderecosItemStateChanged
        Endereco selecionado = (Endereco) cmbEnderecos.getSelectedItem();
        enderecoAtual = selecionado;
        preencheCampos();
    }//GEN-LAST:event_cmbEnderecosItemStateChanged

    private void btnRemoverEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverEnderecoActionPerformed
        if (enderecos.size() < 2) {
            JOptionPane.showMessageDialog(this, "Deve existir ao menos um endereço");
        }
        int resposta = JOptionPane.showConfirmDialog(this, "Esta operação não pode ser desfeita.\nVocê tem certeza?", null, JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            EntityManager em = JPA.getEM();
            enderecoAtual.setDeleted(true);
            em.getTransaction().begin();
            em.merge(enderecoAtual);
            em.close();
            em.getTransaction().commit();
            enderecos.remove(enderecoAtual);
            if (enderecos.size() < 2) {
                cmbEnderecos.setEnabled(false);
            }
        }
        
    }//GEN-LAST:event_btnRemoverEnderecoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bairroTextField;
    private javax.swing.JButton btnAdicionarEndereco;
    private javax.swing.JButton btnRemoverEndereco;
    private javax.swing.JTextField cidadeTextField;
    private javax.swing.JComboBox<Endereco> cmbEnderecos;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblEnderecos;
    private javax.swing.JLabel lblLogradouro;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JTextField txtLogradouro;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JComboBox<UF> ufComboBox;
    // End of variables declaration//GEN-END:variables
}
