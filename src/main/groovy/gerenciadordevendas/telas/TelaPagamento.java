/*
 * GerenciadorDeVendas: TelaPagamento.java
 * Enconding: UTF-8
 * Data de criação: 19/03/2019 17:04:49
 */
package gerenciadordevendas.telas;

import gerenciadordevendas.JPA;
import gerenciadordevendas.controller.CodigoBarrasJasperReports;
import gerenciadordevendas.exception.TransacaoException;
import gerenciadordevendas.model.FormaPagamento;
import gerenciadordevendas.telas.listener.DecimalDocumentListener;
import gerenciadordevendas.telas.listener.InteiroDocumentListener;
import gerenciadordevendas.telas.listener.MoedaDocumentListener;
import gerenciadordevendas.telas.listener.ParcelaDocumentListener;
import gerenciadordevendas.model.Cliente;
import gerenciadordevendas.model.Conta;
import gerenciadordevendas.model.ItemEstoque;
import gerenciadordevendas.model.Parcela;
import gerenciadordevendas.model.Venda;
import gerenciadordevendas.model.Vendedor;
import gerenciadordevendas.tablemodel.ParcelaTableModel;
import gerenciadordevendas.telas.util.TelaUtil;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

/**
 *
 * @author Ramon Porto
 */
public class TelaPagamento extends javax.swing.JDialog {

    private Venda venda;
    private ParcelaTableModel model;

    public TelaPagamento(Venda venda) {
        super(null, Dialog.DEFAULT_MODALITY_TYPE);
        initComponents();
        this.venda = venda;
        new DecimalDocumentListener(txtPorcentagemDesconto, (e) -> atualizaDesconto(e)).inicializa();
        new DecimalDocumentListener(txtDescontoReal, (e) -> atualizaDesconto(e)).inicializa();
        new DecimalDocumentListener(txtValorRecebido, (e) -> atualizaTroco(e)).inicializa();
        new MoedaDocumentListener(txtValorParcela).inicializa();
        new ParcelaDocumentListener(((JSpinner.DefaultEditor) SpinnerParcela.getEditor()).getTextField(), (e) -> atualizaParcelas(e)).inicializa();
        new InteiroDocumentListener(((JSpinner.DefaultEditor) txtIntervalo.getEditor()).getTextField(), (e) -> atualizaParcelas()).inicializa();

        EntityManager em = JPA.getEM();
        TelaUtil.carregarObjetosNaComboBox(em, cmbForma, FormaPagamento.class);
        TelaUtil.carregarObjetosNaComboBox(em, cmbCliente, Cliente.class);
        TelaUtil.carregarObjetosNaComboBox(em, cmbVendedor, Vendedor.class);
        em.close();
//        panelParcelas.setVisible(false);
        panelForma.setVisible(false);

        model = new ParcelaTableModel(tblParcelas, venda);
        preencheCampos();
        tabPagamento.setEnabledAt(1, false);
    }

    private void preencheCampos() {
        cmbVendedor.setSelectedItem(venda.getVendedor());
        cmbCliente.setSelectedItem(venda.getConta().getCliente());
        if (cmbCliente.getSelectedIndex() < 0) {
            radioCrediario.setVisible(false);
        }
        txtSubTotal.setText(venda.getSubTotal().setScale(2, RoundingMode.HALF_UP).toString());
        txtTotal.setText(venda.getSubTotal().setScale(2, RoundingMode.HALF_UP).toString());
        txtJaPago.setText(venda.getSubTotal().setScale(2, RoundingMode.HALF_UP).toString());
        txtValorRecebido.setText(venda.getSubTotal().setScale(2, RoundingMode.HALF_UP).toString());
    }

    private void atualizaTroco(String sValorRecebido) {
        txtValorRecebido.setBackground(Color.WHITE);
        if (sValorRecebido.isEmpty()) {
            sValorRecebido = "0";
        }
        BigDecimal valorRecebido = new BigDecimal(sValorRecebido);
        if (valorRecebido.compareTo(BigDecimal.ZERO) == 0) {
            txtTroco.setText("0.00");
        }
        if (valorRecebido.compareTo(venda.getTotal()) < 0) {
            txtValorRecebido.setBackground(Color.YELLOW);
        }

        txtTroco.setText(valorRecebido.subtract(venda.getTotal()).setScale(2, RoundingMode.HALF_UP).toString());
    }

    private void atualizaDesconto(String e) {

        try {
            if (e.isEmpty()) {
                e = "0";
            }

            BigDecimal desconto = new BigDecimal(e);
            if (radioPorcentagemDesconto.isSelected()) {
                txtPorcentagemDesconto.setBackground(Color.WHITE);
                txtPorcentagemDesconto.setToolTipText(e);
                BigDecimal descontoReal = venda.setPorcentagemDesconto(desconto).setScale(2, RoundingMode.HALF_UP);
                txtDescontoReal.setText(descontoReal.toString());
            } else {
                txtDescontoReal.setBackground(Color.WHITE);
                txtDescontoReal.setToolTipText(e);
                BigDecimal porcentagem = venda.setDescontoReal(desconto).setScale(2, RoundingMode.HALF_UP);
                txtPorcentagemDesconto.setText(porcentagem.toString());
            }
            txtTotal.setText(venda.getTotal().setScale(2, RoundingMode.HALF_UP).toString());
            atualizaTroco(txtValorRecebido.getText());
        } catch (TransacaoException ex) {
            txtTotal.setText(venda.getSubTotal().setScale(2, RoundingMode.HALF_UP).toString());
            if (radioPorcentagemDesconto.isSelected()) {
                txtPorcentagemDesconto.setBackground(Color.YELLOW);
                txtPorcentagemDesconto.setToolTipText(ex.getMessage());
            } else {
                txtDescontoReal.setBackground(Color.YELLOW);
                txtDescontoReal.setToolTipText(ex.getMessage());
            }
        }
    }

    private String getSelectedRadio() {
        Enumeration<AbstractButton> elements = GrupoForma.getElements();
        while (elements.hasMoreElements()) {
            AbstractButton element = elements.nextElement();
            if (element.isSelected()) {
                return element.getText();
            }
        }
        throw new RuntimeException("Não foi identificado o JRadioButton selecionado");
    }

    private FormaPagamento getFormaFromRadio() {
        String selecionado = getSelectedRadio();
        for (int i = 0; i < cmbForma.getItemCount(); i++) {
            FormaPagamento forma = cmbForma.getItemAt(i);
            if (forma.getDescricao().equals(selecionado)) {
                return forma;
            }
        }
        return null;
    }

    private int getPeriodo() {
        String intervalo = (String) cmbIntervalo.getSelectedItem();

        if (intervalo.equals("Dias")) {
            return Calendar.DAY_OF_YEAR;
        } else if (intervalo.equals("Semanas")) {
            return Calendar.WEEK_OF_YEAR;
        }
        if (intervalo.equals("Meses")) {
            return Calendar.MONTH;
        }
        if (intervalo.equals("Anos")) {
            return Calendar.YEAR;
        }
        throw new RuntimeException("Informação inválida para o intervalo");
    }

    private Cliente getClientePadrao(EntityManager em) {
        Cliente padrao = em.find(Cliente.class, 1);
        if (padrao == null) {
            padrao = Cliente.padrao();
            em.getTransaction().begin();
            padrao = em.merge(padrao);
            em.getTransaction().commit();
        }
        return padrao;
    }

    private void atualizaParcelas() {
        atualizaParcelas("" + SpinnerParcela.getValue());
    }

    private void atualizaParcelas(String sParcelas) {
        if (sParcelas.isEmpty()) {
            return;
        }
        List<Parcela> parcelas = new ArrayList<>();
        Date dataParcela = txtIniciarEm.getDate();
        int numeroParcelas = Integer.valueOf(sParcelas);
        if (numeroParcelas < 1) {
            return;
        }
        int intervalo = (int) txtIntervalo.getValue();
        int periodo = getPeriodo();

        BigDecimal total = new BigDecimal(txtTotal.getText());
        BigDecimal valorParcela = total.divide(BigDecimal.valueOf(numeroParcelas), 2, BigDecimal.ROUND_HALF_UP);

        Calendar instance = Calendar.getInstance();
        instance.setTime(dataParcela);

        for (int i = 0; i < numeroParcelas; i++) {
            Parcela parcela = new Parcela();
            parcela.setVencimento(dataParcela);
            FormaPagamento forma = getFormaFromRadio();
            parcela.setFormaPagamento(forma);
            parcela.setVenda(venda);
            parcela.setValor(valorParcela);

            parcelas.add(parcela);
            instance.add(periodo, intervalo);
            dataParcela = instance.getTime();
        }

        venda.setParcelas(parcelas);
        model.setVenda(venda);
    }

    private List<Parcela> gerarParcelaUnicaPaga() {
        BigDecimal valorParcela = new BigDecimal(txtTotal.getText());

        Parcela parcela = new Parcela();
        FormaPagamento forma = getFormaFromRadio();
        parcela.setFormaPagamento(forma);
        parcela.setVenda(venda);
        parcela.setValor(valorParcela);
        parcela.setDataPagamento(Calendar.getInstance().getTime());
        return Collections.singletonList(parcela);
    }

    private void adicionarParcela() throws HeadlessException {
        Date dataParcela = txtIniciarEm.getDate();
        BigDecimal valorParcela = new BigDecimal(txtValorParcela.getText());

        Parcela parcela = new Parcela();
        parcela.setVencimento(dataParcela);
        parcela.setFormaPagamento((FormaPagamento) cmbForma.getSelectedItem());
        parcela.setValor(valorParcela);
        try {
            model.add(parcela);
        } catch (TransacaoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }

        int intervalo = (int) txtIntervalo.getValue();
        int periodo = getPeriodo();

        txtRestanteParcelamento.setText(model.getSaldo().toString());
        txtValorParcela.setText(txtRestanteParcelamento.getText());
        txtJaPagoParcelamento.setText(model.getTotalParcelas().toString());

        Calendar instance = Calendar.getInstance();
        instance.setTime(dataParcela);
        instance.add(periodo, intervalo);
        txtIniciarEm.setDate(instance.getTime());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        GrupoForma = new javax.swing.ButtonGroup();
        popupTabela = new javax.swing.JPopupMenu();
        mnuEditar = new javax.swing.JMenuItem();
        mnuRemover = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        tabPagamento = new javax.swing.JTabbedPane();
        tabDadosGerais = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbVendedor = new javax.swing.JComboBox<>();
        cmbCliente = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnTamanho = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        txtJaPago = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        txtValorRecebido = new javax.swing.JTextField();
        lblTroco = new javax.swing.JLabel();
        txtTroco = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        txtPorcentagemDesconto = new javax.swing.JTextField();
        txtDescontoReal = new javax.swing.JTextField();
        radioPorcentagemDesconto = new javax.swing.JRadioButton();
        radioDescontoReal = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        radioDinheiro = new javax.swing.JRadioButton();
        radioCreditoAvista = new javax.swing.JRadioButton();
        radioCreditoParcelado = new javax.swing.JRadioButton();
        radioDebito = new javax.swing.JRadioButton();
        radioCrediario = new javax.swing.JRadioButton();
        radioCheque = new javax.swing.JRadioButton();
        radioDeposito = new javax.swing.JRadioButton();
        radioMultiplo = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        tabParcelamento = new javax.swing.JPanel();
        panelParcelas = new javax.swing.JPanel();
        lblNumeroParcelas = new javax.swing.JLabel();
        SpinnerParcela = new javax.swing.JSpinner();
        lblIniciarEm = new javax.swing.JLabel();
        txtIniciarEm = new com.toedter.calendar.JDateChooser();
        txtIntervalo = new javax.swing.JSpinner();
        lblIntervalo = new javax.swing.JLabel();
        cmbIntervalo = new javax.swing.JComboBox<>();
        panelForma = new javax.swing.JPanel();
        lblForma = new javax.swing.JLabel();
        cmbForma = new javax.swing.JComboBox<>();
        lblValorParcela = new javax.swing.JLabel();
        txtValorParcela = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        lblTotalParcelamento = new javax.swing.JLabel();
        lblJaPagoParcelamento = new javax.swing.JLabel();
        lblRestanteParcelmanto = new javax.swing.JLabel();
        txtTotalParcelamento = new javax.swing.JTextField();
        txtJaPagoParcelamento = new javax.swing.JTextField();
        txtRestanteParcelamento = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblParcelas = new javax.swing.JTable();
        btnProximo = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();

        mnuEditar.setText("Editar");
        mnuEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditarActionPerformed(evt);
            }
        });
        popupTabela.add(mnuEditar);

        mnuRemover.setText("Remover");
        popupTabela.add(mnuRemover);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tabDadosGerais.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Vendedor");

        cmbVendedor.setEditable(true);

        cmbCliente.setEditable(true);
        cmbCliente.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbClienteItemStateChanged(evt);
            }
        });

        jLabel2.setText("Cliente");

        btnTamanho.setText("i");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pagamento"));

        jLabel3.setText("Subtotal R$");

        jLabel4.setText("Já pago R$");

        jLabel5.setText("Total R$");

        jLabel6.setText("Valor Recebido R$");

        txtSubTotal.setEditable(false);

        txtJaPago.setEditable(false);

        txtTotal.setEditable(false);

        lblTroco.setText("Troco R$");

        txtTroco.setEditable(false);
        txtTroco.setText("0.00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtJaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTroco)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtValorRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTroco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtJaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtValorRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTroco)
                    .addComponent(txtTroco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Desconto"));

        txtPorcentagemDesconto.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtPorcentagemDesconto.setText("0");

        txtDescontoReal.setEditable(false);
        txtDescontoReal.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        radioPorcentagemDesconto.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radioPorcentagemDesconto);
        radioPorcentagemDesconto.setSelected(true);
        radioPorcentagemDesconto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioPorcentagemDescontoItemStateChanged(evt);
            }
        });

        radioDescontoReal.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radioDescontoReal);

        jLabel10.setText("%");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(radioPorcentagemDesconto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPorcentagemDesconto, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(radioDescontoReal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescontoReal)
                        .addGap(15, 15, 15)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioPorcentagemDesconto)
                    .addComponent(txtPorcentagemDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioDescontoReal)
                    .addComponent(txtDescontoReal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Forma de Pagamento"));

        radioDinheiro.setBackground(new java.awt.Color(255, 255, 255));
        GrupoForma.add(radioDinheiro);
        radioDinheiro.setSelected(true);
        radioDinheiro.setText("Dinheiro");
        radioDinheiro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                umaParcelaItemStateChanged(evt);
            }
        });

        radioCreditoAvista.setBackground(new java.awt.Color(255, 255, 255));
        GrupoForma.add(radioCreditoAvista);
        radioCreditoAvista.setText("Cartão de Crédito à vista");
        radioCreditoAvista.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                umaParcelaItemStateChanged(evt);
            }
        });

        radioCreditoParcelado.setBackground(new java.awt.Color(255, 255, 255));
        GrupoForma.add(radioCreditoParcelado);
        radioCreditoParcelado.setText("Cartão de Crédito parcelado");
        radioCreditoParcelado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                parceladosItemStateChanged(evt);
            }
        });

        radioDebito.setBackground(new java.awt.Color(255, 255, 255));
        GrupoForma.add(radioDebito);
        radioDebito.setText("Cartão de Débito");
        radioDebito.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                umaParcelaItemStateChanged(evt);
            }
        });

        radioCrediario.setBackground(new java.awt.Color(255, 255, 255));
        GrupoForma.add(radioCrediario);
        radioCrediario.setText("Crediário");
        radioCrediario.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                parceladosItemStateChanged(evt);
            }
        });

        radioCheque.setBackground(new java.awt.Color(255, 255, 255));
        GrupoForma.add(radioCheque);
        radioCheque.setText("Cheque");
        radioCheque.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                parceladosItemStateChanged(evt);
            }
        });

        radioDeposito.setBackground(new java.awt.Color(255, 255, 255));
        GrupoForma.add(radioDeposito);
        radioDeposito.setText("Deposito");
        radioDeposito.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                parceladosItemStateChanged(evt);
            }
        });

        radioMultiplo.setBackground(new java.awt.Color(255, 255, 255));
        GrupoForma.add(radioMultiplo);
        radioMultiplo.setText("Múltiplo");
        radioMultiplo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioMultiploItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(radioCrediario)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(radioDeposito)
                            .addGap(100, 100, 100)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radioCreditoParcelado)
                            .addComponent(radioCreditoAvista)
                            .addComponent(radioDinheiro)
                            .addComponent(radioDebito)
                            .addComponent(radioCheque)
                            .addComponent(radioMultiplo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(radioDinheiro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioCreditoAvista)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioCreditoParcelado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioDebito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioCrediario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioCheque)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioDeposito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioMultiplo))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Observações"));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout tabDadosGeraisLayout = new javax.swing.GroupLayout(tabDadosGerais);
        tabDadosGerais.setLayout(tabDadosGeraisLayout);
        tabDadosGeraisLayout.setHorizontalGroup(
            tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDadosGeraisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabDadosGeraisLayout.createSequentialGroup()
                        .addGroup(tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabDadosGeraisLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18))
                            .addGroup(tabDadosGeraisLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(31, 31, 31)))
                        .addGroup(tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(208, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabDadosGeraisLayout.createSequentialGroup()
                        .addGroup(tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(tabDadosGeraisLayout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18))))
        );
        tabDadosGeraisLayout.setVerticalGroup(
            tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDadosGeraisLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTamanho))
                .addGap(11, 11, 11)
                .addGroup(tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPagamento.addTab("Dados Gerais", tabDadosGerais);

        tabParcelamento.setBackground(new java.awt.Color(255, 255, 255));

        panelParcelas.setBackground(new java.awt.Color(255, 255, 255));
        panelParcelas.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcelas"));

        lblNumeroParcelas.setText("Parcelas");

        SpinnerParcela.setModel(new javax.swing.SpinnerNumberModel(2, 1, null, 1));

        lblIniciarEm.setText("Iniciar em");

        txtIntervalo.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        lblIntervalo.setText("Com intervalos de");

        cmbIntervalo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dias", "Semanas", "Meses", "Anos" }));
        cmbIntervalo.setSelectedIndex(2);

        panelForma.setBackground(new java.awt.Color(255, 255, 255));
        panelForma.setPreferredSize(new java.awt.Dimension(200, 20));

        lblForma.setText("Forma");

        lblValorParcela.setText("Valor R$");

        txtValorParcela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorParcelaKeyPressed(evt);
            }
        });

        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFormaLayout = new javax.swing.GroupLayout(panelForma);
        panelForma.setLayout(panelFormaLayout);
        panelFormaLayout.setHorizontalGroup(
            panelFormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelFormaLayout.createSequentialGroup()
                .addGroup(panelFormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormaLayout.createSequentialGroup()
                        .addComponent(lblValorParcela)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(panelFormaLayout.createSequentialGroup()
                        .addComponent(lblForma)
                        .addGap(20, 20, 20)))
                .addGroup(panelFormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormaLayout.createSequentialGroup()
                        .addComponent(txtValorParcela)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdicionar))
                    .addComponent(cmbForma, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelFormaLayout.setVerticalGroup(
            panelFormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormaLayout.createSequentialGroup()
                .addGroup(panelFormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbForma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFormaLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblForma)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtValorParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblValorParcela))
                    .addComponent(btnAdicionar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelParcelasLayout = new javax.swing.GroupLayout(panelParcelas);
        panelParcelas.setLayout(panelParcelasLayout);
        panelParcelasLayout.setHorizontalGroup(
            panelParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelParcelasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelForma, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addGroup(panelParcelasLayout.createSequentialGroup()
                        .addGroup(panelParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelParcelasLayout.createSequentialGroup()
                                .addComponent(lblNumeroParcelas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SpinnerParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblIniciarEm))
                            .addGroup(panelParcelasLayout.createSequentialGroup()
                                .addComponent(lblIntervalo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIntervalo)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelParcelasLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cmbIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtIniciarEm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(40, 40, 40))
        );
        panelParcelasLayout.setVerticalGroup(
            panelParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelParcelasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNumeroParcelas)
                        .addComponent(SpinnerParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblIniciarEm))
                    .addComponent(txtIniciarEm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIntervalo)
                    .addComponent(txtIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelForma, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Pagamento"));

        lblTotalParcelamento.setText("Total R$");

        lblJaPagoParcelamento.setText("Já pago R$");

        lblRestanteParcelmanto.setText("Restante R$");

        txtTotalParcelamento.setEditable(false);
        txtTotalParcelamento.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txtJaPagoParcelamento.setEditable(false);
        txtJaPagoParcelamento.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtJaPagoParcelamento.setText("0");

        txtRestanteParcelamento.setEditable(false);
        txtRestanteParcelamento.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(lblRestanteParcelmanto)
                        .addGap(18, 18, 18)
                        .addComponent(txtRestanteParcelamento, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(lblJaPagoParcelamento)
                        .addGap(18, 18, 18)
                        .addComponent(txtJaPagoParcelamento, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(lblTotalParcelamento)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotalParcelamento, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalParcelamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalParcelamento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJaPagoParcelamento)
                    .addComponent(txtJaPagoParcelamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRestanteParcelmanto)
                    .addComponent(txtRestanteParcelamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseReleased(evt);
            }
        });

        tblParcelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblParcelas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblParcelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblParcelasMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblParcelas);

        javax.swing.GroupLayout tabParcelamentoLayout = new javax.swing.GroupLayout(tabParcelamento);
        tabParcelamento.setLayout(tabParcelamentoLayout);
        tabParcelamentoLayout.setHorizontalGroup(
            tabParcelamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabParcelamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabParcelamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabParcelamentoLayout.createSequentialGroup()
                        .addComponent(panelParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabParcelamentoLayout.setVerticalGroup(
            tabParcelamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabParcelamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabParcelamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelParcelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPagamento.addTab("Parcelamento", tabParcelamento);

        btnProximo.setText("Finalizar");
        btnProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProximoActionPerformed(evt);
            }
        });

        btnAnterior.setText("Anterior");
        btnAnterior.setEnabled(false);
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnProximo, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tabPagamento))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPagamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProximo)
                    .addComponent(btnAnterior))
                .addContainerGap())
        );

        tabPagamento.getAccessibleContext().setAccessibleName("Dados Gerais");
        tabPagamento.getAccessibleContext().setAccessibleDescription("");

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

    private void radioPorcentagemDescontoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioPorcentagemDescontoItemStateChanged
        if (radioPorcentagemDesconto.isSelected()) {
            txtPorcentagemDesconto.setEnabled(true);
            txtDescontoReal.setEnabled(false);
        } else {
            txtPorcentagemDesconto.setEnabled(false);
            txtDescontoReal.setEnabled(true);
        }
    }//GEN-LAST:event_radioPorcentagemDescontoItemStateChanged

    private void umaParcelaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_umaParcelaItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            btnProximo.setText("Finalizar");
            txtValorRecebido.setEnabled(true);
            venda.setParcelas(new ArrayList<>());
            model.setVenda(venda);
        }
    }//GEN-LAST:event_umaParcelaItemStateChanged

    private void radioMultiploItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioMultiploItemStateChanged
        panelForma.setVisible(radioMultiplo.isSelected());
        SpinnerParcela.setVisible(!radioMultiplo.isSelected());
        lblNumeroParcelas.setVisible(!radioMultiplo.isSelected());
        btnAdicionar.setVisible(radioMultiplo.isSelected());
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            btnProximo.setText("Próximo");
            venda.setParcelas(new ArrayList<>());
            model.setVenda(venda);
        }
    }//GEN-LAST:event_radioMultiploItemStateChanged

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        adicionarParcela();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed

        if (btnProximo.getText().equals("Próximo")) {
            txtTotalParcelamento.setText(txtTotal.getText());
            txtRestanteParcelamento.setText(txtTotal.getText());

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            txtIniciarEm.setDate(calendar.getTime());
            txtValorParcela.setText(txtTotal.getText());
            if (!radioMultiplo.isSelected()) {
                mnuRemover.setEnabled(false);
                txtJaPagoParcelamento.setText(txtTotal.getText());
                atualizaParcelas();
            } else {
                mnuRemover.setEnabled(true);
                txtJaPagoParcelamento.setText("0.00");
            }

            tabPagamento.setEnabledAt(0, false);
            tabPagamento.setEnabledAt(1, true);
            tabPagamento.setSelectedComponent(tabParcelamento);
            btnProximo.setText("Finalizar");
            btnAnterior.setEnabled(true);
        } else {
            if (radioDinheiro.isSelected() || radioCreditoAvista.isSelected() || radioDebito.isSelected()) {
                venda.setParcelas(gerarParcelaUnicaPaga());
            }

            if (!venda.getParcelas().isEmpty()) {
                EntityManager em = JPA.getEM();
                if (venda.getConta() == null) {
                    venda.setConta(getClientePadrao(em).getConta());
                }

                venda.finalizarVenda();

                em.getTransaction().begin();
                venda = em.merge(venda);
                em.getTransaction().commit();
                
                int resposta = JOptionPane.showConfirmDialog(this, "Deseja imprimir o recibo?", "Impressão", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    Map<String, Object> parametros = new HashMap<>();
                    Optional matriz = em.createQuery("SELECT e FROM Empresa e WHERE e.tipoEmpresa = :x").getResultStream().findAny();
                    if (matriz.isPresent()) {
                        parametros.put("FILIAL", null);
                        try {
                            JasperPrint print = JasperFillManager.fillReport("./print/recibo.jasper", parametros, new JREmptyDataSource());

                            JasperPrintManager.printPage(print, 0, true);
                        } catch (JRException ex) {
                            Logger.getLogger(CodigoBarrasJasperReports.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Não foi possível imprimir, pois não há nenhuma matriz cadastrada");
                    }
                }
                em.close();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Adicione pelo menos uma parcela para finalizar a venda");
            }
        }
    }//GEN-LAST:event_btnProximoActionPerformed

    private void cmbClienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbClienteItemStateChanged
        if (cmbCliente.getSelectedIndex() > -1) {
            radioCrediario.setVisible(true);
        } else {
            radioCrediario.setVisible(false);
        }
    }//GEN-LAST:event_cmbClienteItemStateChanged

    private void parceladosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_parceladosItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            btnAdicionar.setVisible(false);

            txtValorRecebido.setEnabled(false);
            btnProximo.setText("Próximo");
            model.atualizaForma(getFormaFromRadio());
        }
    }//GEN-LAST:event_parceladosItemStateChanged

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        btnProximo.setText("Próximo");
        tabPagamento.setEnabledAt(0, true);
        tabPagamento.setEnabledAt(1, false);
        tabPagamento.setSelectedComponent(tabDadosGerais);
        btnAnterior.setEnabled(false);
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void txtValorParcelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorParcelaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            adicionarParcela();
        }
    }//GEN-LAST:event_txtValorParcelaKeyPressed

    private void mnuEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditarActionPerformed
        int row = tblParcelas.getSelectedRow();
        if (row > -1) {

            model.editar(this);

            BigDecimal jaPago = model.getTotalParcelas();
            BigDecimal restante = model.getSaldo();
            txtRestanteParcelamento.setText(restante.toString());
            txtValorParcela.setText(restante.toString());
            txtJaPagoParcelamento.setText(jaPago.toString());

        }
    }//GEN-LAST:event_mnuEditarActionPerformed

    private void jScrollPane1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseReleased
        if (SwingUtilities.isRightMouseButton(evt)) {
            popupTabela.show(evt.getComponent(), evt.getX(), evt.getY());
        }

    }//GEN-LAST:event_jScrollPane1MouseReleased

    private void tblParcelasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblParcelasMouseReleased
        SwingUtilities.invokeLater(() -> {
            int linha = tblParcelas.rowAtPoint(evt.getPoint());
            if (linha >= 0 && linha < tblParcelas.getRowCount()) {
                tblParcelas.setRowSelectionInterval(linha, linha);
            }
            if (SwingUtilities.isRightMouseButton(evt)) {
                popupTabela.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        });
    }//GEN-LAST:event_tblParcelasMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPagamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Venda v = new Venda(Conta.padrao(), Vendedor.padrao());
                v.setSubTotal(BigDecimal.TEN);
                v.setTotal(BigDecimal.TEN);
                TelaPagamento dialog = new TelaPagamento(v);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GrupoForma;
    private javax.swing.JSpinner SpinnerParcela;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnTamanho;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<Cliente> cmbCliente;
    private javax.swing.JComboBox<FormaPagamento> cmbForma;
    private javax.swing.JComboBox<String> cmbIntervalo;
    private javax.swing.JComboBox<Vendedor> cmbVendedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblForma;
    private javax.swing.JLabel lblIniciarEm;
    private javax.swing.JLabel lblIntervalo;
    private javax.swing.JLabel lblJaPagoParcelamento;
    private javax.swing.JLabel lblNumeroParcelas;
    private javax.swing.JLabel lblRestanteParcelmanto;
    private javax.swing.JLabel lblTotalParcelamento;
    private javax.swing.JLabel lblTroco;
    private javax.swing.JLabel lblValorParcela;
    private javax.swing.JMenuItem mnuEditar;
    private javax.swing.JMenuItem mnuRemover;
    private javax.swing.JPanel panelForma;
    private javax.swing.JPanel panelParcelas;
    private javax.swing.JPopupMenu popupTabela;
    private javax.swing.JRadioButton radioCheque;
    private javax.swing.JRadioButton radioCrediario;
    private javax.swing.JRadioButton radioCreditoAvista;
    private javax.swing.JRadioButton radioCreditoParcelado;
    private javax.swing.JRadioButton radioDebito;
    private javax.swing.JRadioButton radioDeposito;
    private javax.swing.JRadioButton radioDescontoReal;
    private javax.swing.JRadioButton radioDinheiro;
    private javax.swing.JRadioButton radioMultiplo;
    private javax.swing.JRadioButton radioPorcentagemDesconto;
    private javax.swing.JPanel tabDadosGerais;
    private javax.swing.JTabbedPane tabPagamento;
    private javax.swing.JPanel tabParcelamento;
    private javax.swing.JTable tblParcelas;
    private javax.swing.JTextField txtDescontoReal;
    private com.toedter.calendar.JDateChooser txtIniciarEm;
    private javax.swing.JSpinner txtIntervalo;
    private javax.swing.JTextField txtJaPago;
    private javax.swing.JTextField txtJaPagoParcelamento;
    private javax.swing.JTextField txtPorcentagemDesconto;
    private javax.swing.JTextField txtRestanteParcelamento;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalParcelamento;
    private javax.swing.JTextField txtTroco;
    private javax.swing.JTextField txtValorParcela;
    private javax.swing.JTextField txtValorRecebido;
    // End of variables declaration//GEN-END:variables

}
