/*
 * GerenciadorDeVendas: EnderecoPanel.java
 * Enconding: UTF-8
 * Data de criação: 11/04/2019 11:01:31
 */
package gerenciadordevendas.telas;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.Endereco;
import gerenciadordevendas.model.UF;
import gerenciadordevendas.tablemodel.EnderecoTableModel;
import gerenciadordevendas.telas.listener.NumeroListener;
import gerenciadordevendas.telas.listener.NumeroTextoListener;
import gerenciadordevendas.telas.listener.TextoListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Ramon Porto
 */
public class EnderecoPanel extends javax.swing.JPanel {

    private List<Endereco>  enderecos = new ArrayList<>();
    private Endereco enderecoAtual;
    private boolean salvo = false;

    public EnderecoPanel() {
        initComponents();
        this.enderecoAtual = new Endereco();
        enderecos.add(enderecoAtual);
        inicializar(enderecoAtual);
    }
    
    public EnderecoPanel(List<Endereco> enderecos) {
        if (enderecos.isEmpty()) {
            enderecos.add(new Endereco());
        }
        initComponents();
        this.enderecos = enderecos;
        inicializar(enderecos.get(0));
    }
    
    public EnderecoPanel(Endereco enderecoAtual) {
        initComponents();
        
        inicializar(enderecoAtual);
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        if (enderecos == null) {
            enderecos = new ArrayList<>();
            enderecos.add(new Endereco());
        }
        this.enderecos = enderecos;
        inicializar(enderecos.get(0));
    }
    
    
    
    private void inicializar(Endereco enderecoAtual) {
        this.enderecoAtual = enderecoAtual;
        preencheCampos();
        addDocumentListener(txtLogradouro, new NumeroTextoListener(100));
        addDocumentListener(txtComplemento, new NumeroTextoListener(100));
        addDocumentListener(txtNumero, new NumeroListener(5));
        addDocumentListener(bairroTextField, new NumeroTextoListener(20));
        addDocumentListener(cidadeTextField, new TextoListener(40));
    }

    public boolean isSalvo() {
        return salvo;
    }

    public void validar() {
        enderecoAtual.setLogradouro(txtLogradouro.getText());
        enderecoAtual.setBairro(bairroTextField.getText());
        enderecoAtual.setNumero(txtNumero.getText().isEmpty()? null: Integer.valueOf(txtNumero.getText()));
        enderecoAtual.setCidade(cidadeTextField.getText());
        enderecoAtual.setCep(txtCep.getText());
        enderecoAtual.setUf((UF) ufComboBox.getSelectedItem());
    }

    private void preencheCampos() {
        txtLogradouro.setText(enderecoAtual.getLogradouro());
        txtNumero.setText((enderecoAtual.getNumero() == null ? "" : ("" + enderecoAtual.getNumero())));
        txtComplemento.setText(enderecoAtual.getComplemento());
        cidadeTextField.setText(enderecoAtual.getCidade());
        bairroTextField.setText(enderecoAtual.getBairro());
        txtCep.setText(enderecoAtual.getCep());
        ufComboBox.setSelectedItem(Optional.ofNullable(enderecoAtual.getUf()).orElse(UF.SC));

    }

    private void addDocumentListener(JTextField field, DocumentListener listener) {
        field.getDocument().addDocumentListener(listener);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        btnSalvar = new javax.swing.JButton();
        btnEndereços = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel9.setText("UF");

        try {
            txtCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCep.setText("");

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnEndereços.setText("Endereços");
        btnEndereços.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEndereçosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblLogradouro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bairroTextField)
                            .addComponent(cidadeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addComponent(jLabel9)
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ufComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtLogradouro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblComplemento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCep, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(btnEndereços, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogradouro)
                    .addComponent(txtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEndereços))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComplemento)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumero)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bairroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(ufComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCidade)
                    .addComponent(cidadeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        validar();
        EntityManager em = JPA.getEM();

        em.getTransaction().begin();
        em.merge(enderecoAtual);

        em.getTransaction().commit();
        em.close();
        salvo = true;
        JFrame framePai = (JFrame) SwingUtilities.getWindowAncestor(this);
        framePai.dispose();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnEndereçosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEndereçosActionPerformed
        new TelaPesquisar(new EnderecoTableModel(enderecos), false).setVisible(true);
    }//GEN-LAST:event_btnEndereçosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bairroTextField;
    private javax.swing.JButton btnEndereços;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JTextField cidadeTextField;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblLogradouro;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JTextField txtLogradouro;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JComboBox<UF> ufComboBox;
    // End of variables declaration//GEN-END:variables
}
