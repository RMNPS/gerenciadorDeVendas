/*
 * GerenciadorDeVendas: EnderecoPanel.java
 * Enconding: UTF-8
 * Data de criação: 11/04/2019 11:01:31
 */
package gerenciadordevendas.telas;

import gerenciadordevendas.model.Endereco;
import gerenciadordevendas.model.UF;
import gerenciadordevendas.telas.listener.NumeroListener;
import gerenciadordevendas.telas.listener.NumeroTextoListener;
import gerenciadordevendas.telas.listener.TextoListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Ramon Porto
 */
public class EnderecoPanel extends javax.swing.JPanel {

    private List<Endereco> enderecos = new ArrayList<>();
    private Endereco enderecoAtual;

    public EnderecoPanel(Endereco endereco) {
        initComponents();
        cmbEnderecos.setVisible(false);
        lblEnderecos.setVisible(false);
        this.enderecoAtual = endereco;

        inicializar();
    }

    private void inicializar() {
        
        addDocumentListener(txtLogradouro, new NumeroTextoListener(100));
        addDocumentListener(txtLogradouro, new NumeroListener(5));
        addDocumentListener(bairroTextField, new TextoListener(20));
        addDocumentListener(cidadeTextField, new TextoListener(40));

        preencheCampos();
    }

    public EnderecoPanel(List<Endereco> enderecos) {
        initComponents();
        this.enderecos = enderecos;
        this.enderecoAtual = enderecos.get(0);

        inicializar();
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
    }

    private void addDocumentListener(JTextField field, DocumentListener listener) {
        field.getDocument().addDocumentListener(listener);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        lblLogradouro = new javax.swing.JLabel();
        txtLogradouro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCep = new javax.swing.JFormattedTextField();
        bairroTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ufComboBox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        lblCidade = new javax.swing.JLabel();
        cidadeTextField = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        lblNumero = new javax.swing.JLabel();
        lblComplemento = new javax.swing.JLabel();
        txtComplemento = new javax.swing.JTextField();
        cmbEnderecos = new javax.swing.JComboBox<>();
        lblEnderecos = new javax.swing.JLabel();
        btnAdicionarEndereco = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Endereco"));

        lblLogradouro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLogradouro.setText("Endereço");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("CEP");

        try {
            txtCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCep.setText("88980-000");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Bairro");

        ufComboBox.setModel(new DefaultComboBoxModel(UF.values()));

        jLabel9.setText("UF");

        lblCidade.setText("Cidade");

        cidadeTextField.setText("Passo de Torres");

        lblNumero.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumero.setText("Número");

        lblComplemento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblComplemento.setText("Complemento");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCidade))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLogradouro)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bairroTextField)
                                    .addComponent(cidadeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ufComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(lblNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblComplemento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtComplemento)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogradouro)
                    .addComponent(txtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComplemento)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumero)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bairroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ufComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCidade)
                    .addComponent(cidadeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        cmbEnderecos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEnderecosItemStateChanged(evt);
            }
        });

        lblEnderecos.setText("Endereço");

        btnAdicionarEndereco.setText("Adicionar outro");
        btnAdicionarEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarEnderecoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(lblEnderecos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbEnderecos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8)
                        .addComponent(btnAdicionarEndereco))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEnderecos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEnderecos)
                    .addComponent(btnAdicionarEndereco))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarEnderecoActionPerformed
        cmbEnderecos.setVisible(true);
        lblEnderecos.setVisible(true);
        validar();
        enderecoAtual = new Endereco();

        enderecos.add(enderecoAtual);
        preencheCampos();
    }//GEN-LAST:event_btnAdicionarEnderecoActionPerformed

    private void cmbEnderecosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEnderecosItemStateChanged
        Endereco selecionado = (Endereco) cmbEnderecos.getSelectedItem();
    }//GEN-LAST:event_cmbEnderecosItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bairroTextField;
    private javax.swing.JButton btnAdicionarEndereco;
    private javax.swing.JTextField cidadeTextField;
    private javax.swing.JComboBox<Endereco> cmbEnderecos;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
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
