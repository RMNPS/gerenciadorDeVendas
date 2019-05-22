package gerenciadordevendas.telas;

import gerenciadordevendas.ImageService;
import gerenciadordevendas.EntityService;
import gerenciadordevendas.JPA;
import gerenciadordevendas.Regras;
import gerenciadordevendas.exception.FormatacaoException;
import gerenciadordevendas.formatador.Formatador;
import gerenciadordevendas.model.Empresa;
import gerenciadordevendas.model.ItemEstoque;
import gerenciadordevendas.model.Produto;
import gerenciadordevendas.model.Cor;
import gerenciadordevendas.tablemodel.ItemNomeavelTableModel;
import gerenciadordevendas.tablemodel.ProdutoTableModel;
import gerenciadordevendas.model.Tamanho;
import gerenciadordevendas.model.TipoEmpresa;
import gerenciadordevendas.tablemodel.FornecedorTableModel;
import gerenciadordevendas.telas.listener.DecimalDocumentListener;
import gerenciadordevendas.telas.listener.NumeroListener;
import gerenciadordevendas.telas.util.TelaUtil;
import java.awt.Color;
import java.awt.HeadlessException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class TelaItemEstoque extends javax.swing.JDialog {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    private static final ImageService IMAGE_SERVICE = new ImageService();
    private ItemEstoque itemEstoque;
    private List<Produto> produtos;
    private List<Empresa> fornecedores;
    private boolean custoOrdenado = false;
    private double quantidadeTotalOriginal;
    
    public TelaItemEstoque(java.awt.Window parent) {
        this(parent, null);
    }
    
    public TelaItemEstoque(java.awt.Window parent, ItemEstoque itemEstoque) {
        super(parent, java.awt.Dialog.DEFAULT_MODALITY_TYPE);
        df.setParseBigDecimal(true);
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        initComponents();

        EntityManager em = JPA.getEM();
        TelaUtil.carregarEmpresaNaComboBox(em, cmbFornecedor, TipoEmpresa.FORNECEDOR);
        TelaUtil.carregarObjetosNaComboBox(em, cmbProdutos, Produto.class);
        TelaUtil.carregarObjetosNaComboBox(em, cmbTamanho, Tamanho.class);
        TelaUtil.carregarObjetosNaComboBox(em, cmbCor, Cor.class);
//        carregarFornecedoresNaComboBox(em);
//        carregarProdutosNaComboBox(em);
        em.close();
        
        if (itemEstoque == null) {
            this.itemEstoque = new ItemEstoque();
        } else {
            this.itemEstoque = itemEstoque;
            preencheCampos(itemEstoque);
        }
        if (this.itemEstoque.getId() == 0) {
            txtID.setText("" + EntityService.getLastIDplus1(ItemEstoque.class));
        }
        inicializaListeners();
    }

    private void inicializaListeners() {
        new DecimalDocumentListener(txtCustoTotal, (e) -> calculaCustoUN(e)).inicializa();
        new DecimalDocumentListener(txtCustoUN, (e) -> calculaCustoTotal(e)).inicializa();
        new DecimalDocumentListener(txtQnt, (e) -> atualizaQuantidadeIE(e)).inicializa();
//        new DecimalDocumentListener(txtCodigoBarras, (e) -> atualizaCodigoBarras(e)).inicializa();
        new DecimalDocumentListener(txtValorAprazo, (e) -> calculaMargem(e, txtCustoUN.getText())).inicializa();
        new DecimalDocumentListener(txtMargem).inicializa();


        txtNumeroParcelas.getDocument().addDocumentListener(new NumeroListener(20));
        txtCodigoBarras.getDocument().addDocumentListener(new NumeroListener(20));
    }

    private void inicializaCampoNumero(JTextField campo) {
        campo.setText(campo.getText().isEmpty() ? "1" : campo.getText());
    }
    
    private BigDecimal parse(String value) {
        try {
            return (BigDecimal) df.parse(value);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private void calculaCustoUN(String sCustoTotal) {
        if (sCustoTotal.isEmpty()) {
            txtCustoUN.setText("0,00");
            return;
        }

        BigDecimal custoTotal = parse(sCustoTotal);

        inicializaCampoNumero(txtQnt);
        BigDecimal quantidade = parse(txtQnt.getText());
        BigDecimal custoUN = BigDecimal.ZERO;

        if (quantidade.compareTo(BigDecimal.ZERO) > 0) {
            custoUN = custoTotal.divide(quantidade, 2, BigDecimal.ROUND_HALF_UP);
        }
        txtCustoUN.setText(df.format(custoUN));

        calculaMargem(txtValorAprazo.getText(), custoUN.toString());

        custoOrdenado = true;
    }
    
    private void calculaCustoTotal(String sCustoUN) {
        if (sCustoUN.isEmpty()) {
            return;
        }

        BigDecimal custoUN = parse(sCustoUN);
        inicializaCampoNumero(txtQnt);

        BigDecimal quantidade = parse(txtQnt.getText());
        BigDecimal custoTotal = custoUN.multiply(quantidade).setScale(2, RoundingMode.HALF_UP);

        txtCustoTotal.setText(df.format(custoTotal));

        calculaMargem(txtValorAprazo.getText(), sCustoUN);
        custoOrdenado = false;
    }
    
    private void atualizaNumeroParcelas(String text) {
        if (text.isEmpty()) {
            return;
        }

        if (!txtValorAprazo.getText().isEmpty()) {
            BigDecimal valorParcela = BigDecimal.ZERO;
            BigDecimal numeroParcelas = parse(text);
            
            if (numeroParcelas.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal valorAprazo = parse(txtValorAprazo.getText());
                valorParcela = valorAprazo.divide(numeroParcelas, 2, BigDecimal.ROUND_HALF_UP);
            }
            txtValorParcela.setText(df.format(valorParcela));
        }
    }

    private void atualizaQuantidadeIE(String text) {
        if (text.isEmpty()) {
            if (!txtCustoTotal.getText().isEmpty() && custoOrdenado) {
                txtCustoUN.setText("0,00");
            } else if (!txtCustoUN.getText().isEmpty()) {
                txtCustoTotal.setText("0,00");
            }
            return;
        }

        BigDecimal quantidadeIE = parse(text);
        
        Produto p = (Produto) cmbProdutos.getSelectedItem();
        if (p != null) {
            txtQuantidade.setText(df.format(quantidadeTotalOriginal + quantidadeIE.doubleValue() - itemEstoque.getQuantidade()));
        }

        if (!txtCustoTotal.getText().isEmpty() && custoOrdenado) {
            BigDecimal custoTotal = parse(txtCustoTotal.getText());
            BigDecimal custoUN = BigDecimal.ZERO;
            if (quantidadeIE.compareTo(BigDecimal.ZERO) > 0) {

                custoUN = custoTotal.divide(quantidadeIE, 2, BigDecimal.ROUND_HALF_UP);
            }
            txtCustoUN.setText(custoUN.toString());
        } else if (!txtCustoUN.getText().isEmpty()) {
            BigDecimal custoUN = parse(txtCustoUN.getText());
            BigDecimal custoTotal = custoUN.multiply(quantidadeIE);
            txtCustoTotal.setText(custoTotal.toString());
        }

    }

    private void atualizaViaMargem(String text) {
        if (text.isEmpty()) {
            return;
        }
        if (text.equals(".")){
            text = "0";
        }
        BigDecimal margem = parse(text);
        
        if (txtCustoUN.getText().isEmpty()) {
            txtValorAprazo.setText("0,00");
        } else {
            BigDecimal custo = parse(txtCustoUN.getText());
            calculaValorVenda(margem, custo);
        }
    }

    

    private void calculaMargem(String sValorAprazo, String custoUN) {
        if (sValorAprazo.isEmpty() || custoUN.isEmpty()) {
            txtMargem.setText("0,00");
            return;
        }
        BigDecimal valorAprazo = parse(sValorAprazo);
        BigDecimal custo = parse(custoUN);
        if (valorAprazo.compareTo(BigDecimal.ZERO) == 0 || custo.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        
        BigDecimal margem = valorAprazo.divide(custo, 4, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE).multiply(new BigDecimal("100")).setScale(2);
        txtMargem.setText(df.format(margem));
    }

    private void calculaValorVenda(BigDecimal margem, BigDecimal custoUN) {
        BigDecimal venda = custoUN.add(custoUN.multiply(margem.setScale(2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("100")), new MathContext(3))).setScale(2);
        txtValorAprazo.setText(df.format(venda));
    }
    
    
    

    private void preencheCampos(ItemEstoque ie) {
        if (ie != null) {
            BigDecimal custo = ie.getValorCusto().setScale(2, RoundingMode.HALF_UP);
            txtValorAvista.setText(df.format(ie.getValorAvista()));
            txtValorAprazo.setText(df.format(ie.getValorAprazo()));
            if (custo.compareTo(BigDecimal.ZERO) != 0) {
                calculaMargem(df.format(ie.getValorAprazo()), df.format(custo));
            }
            txtCustoUN.setText(df.format(custo.setScale(2, RoundingMode.HALF_UP)));
            txtCustoTotal.setText(df.format(custo.multiply(BigDecimal.valueOf(ie.getQuantidade())).setScale(2, RoundingMode.HALF_UP)));
            txtNumeroParcelas.setText("" + ie.getNumeroParcelas());
            txtValorParcela.setText(df.format(ie.getValorParcelaSugerida()));
            if (ie.getProduto() != null) {
                EntityManager em = JPA.getEM();
                Double quantidade = (Double) em.createQuery("SELECT sum(e.quantidade) FROM ItemEstoque e WHERE e.produto = :p")
                        .setParameter("p", ie.getProduto())
                        .getSingleResult();
                em.close();
                if (quantidade == null) {
                    quantidade = 0d;
                }
                quantidadeTotalOriginal = quantidade;
                txtQuantidade.setText(df.format(quantidade));
            }
            cmbProdutos.setSelectedItem(ie.getProduto());
            cmbFornecedor.setSelectedItem(ie.getFornecedor());
            cmbTamanho.setSelectedItem(ie.getTamanho());
            cmbCor.setSelectedItem(ie.getCor());
            
            txtValidade.setDate(ie.getValidade());
            txtQnt.setText(df.format(ie.getQuantidade()));
            if (ie.getCodigoBarras() != null) {
                txtCodigoBarras.setText(ie.getCodigoBarras());
            } else {
                txtCodigoBarras.setText("");
            }

            txtID.setText(""+ie.getId());
            panelAdicionarImagem1.setCaminhoImagemEntidade(ie.getImagem());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblProduto = new javax.swing.JLabel();
        cmbProdutos = new javax.swing.JComboBox<>();
        btnEditar = new javax.swing.JButton();
        lblFornencedor = new javax.swing.JLabel();
        cmbFornecedor = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        lblCustoUN = new javax.swing.JLabel();
        txtCustoUN = new javax.swing.JTextField();
        txtMargem = new javax.swing.JTextField();
        lblMargem = new javax.swing.JLabel();
        lblValorAvista = new javax.swing.JLabel();
        txtValorAvista = new javax.swing.JTextField();
        lblCustoTotal = new javax.swing.JLabel();
        lblQuantidade = new javax.swing.JLabel();
        txtCustoTotal = new javax.swing.JTextField();
        txtQnt = new javax.swing.JTextField();
        lblValorAprazo = new javax.swing.JLabel();
        txtValorAprazo = new javax.swing.JTextField();
        lblNumeroParcelas = new javax.swing.JLabel();
        txtNumeroParcelas = new javax.swing.JTextField();
        lblValorParcela = new javax.swing.JLabel();
        txtValorParcela = new javax.swing.JTextField();
        btnCalculaVenda = new javax.swing.JButton();
        btnNumeroParcelas = new javax.swing.JButton();
        btnDesc = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        btnClonar = new javax.swing.JButton();
        salvarButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblValidade = new javax.swing.JLabel();
        lblQuantidadeEstoque = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        txtValidade = new com.toedter.calendar.JDateChooser();
        lblCor = new javax.swing.JLabel();
        cmbCor = new javax.swing.JComboBox<>();
        btnCor = new javax.swing.JButton();
        lblTamanho = new javax.swing.JLabel();
        cmbTamanho = new javax.swing.JComboBox<>();
        btnTamanho = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtCodigoBarras = new javax.swing.JTextField();
        lblID = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        panelAdicionarImagem1 = new gerenciadordevendas.telas.PanelAdicionarImagem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Estoque");
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do item selecionado"));

        lblProduto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblProduto.setText("Produto");

        cmbProdutos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbProdutosItemStateChanged(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        lblFornencedor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFornencedor.setText("Fornecedor");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblCustoUN.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCustoUN.setText("Custo UN R$");

        txtCustoUN.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCustoUN.setText("0,00");
        txtCustoUN.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCustoUNFocusGained(evt);
            }
        });

        txtMargem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMargem.setText("120,00");
        txtMargem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMargemFocusLost(evt);
            }
        });
        txtMargem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMargemActionPerformed(evt);
            }
        });

        lblMargem.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMargem.setText("Margem %");

        lblValorAvista.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblValorAvista.setText("Valor à Vista (UN) R$");

        txtValorAvista.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValorAvista.setText("0,00");

        lblCustoTotal.setText("Valor de Custo Total R$");

        lblQuantidade.setText("Quantidade");

        txtCustoTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCustoTotal.setEnabled(false);
        txtCustoTotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCustoTotalFocusGained(evt);
            }
        });

        txtQnt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtQnt.setText("1");

        lblValorAprazo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblValorAprazo.setText("Valor à Prazo (UN) R$");

        txtValorAprazo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValorAprazo.setText("0,00");

        lblNumeroParcelas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumeroParcelas.setText("Número de Parcelas");

        txtNumeroParcelas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumeroParcelas.setText("1");

        lblValorParcela.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblValorParcela.setText("Valor Parcela R$");

        txtValorParcela.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValorParcela.setText("0,00");

        btnCalculaVenda.setText("=");
        btnCalculaVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculaVendaActionPerformed(evt);
            }
        });

        btnNumeroParcelas.setText("=");
        btnNumeroParcelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumeroParcelasActionPerformed(evt);
            }
        });

        btnDesc.setText("desc");
        btnDesc.setToolTipText("Aplicar desconto padrão");
        btnDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescActionPerformed(evt);
            }
        });

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMargem, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCustoTotal, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblQuantidade, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMargem)
                            .addComponent(txtQnt, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txtCustoTotal)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(lblValorAprazo)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtValorAprazo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblValorAvista)
                                            .addComponent(lblNumeroParcelas)
                                            .addComponent(lblValorParcela))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNumeroParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtValorAvista, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtValorParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(107, 107, 107)
                                .addComponent(lblCustoUN)
                                .addGap(18, 18, 18)
                                .addComponent(txtCustoUN)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCalculaVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumeroParcelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCustoUN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCustoUN, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQuantidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCustoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCustoTotal)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMargem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMargem)
                    .addComponent(btnCalculaVenda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorAprazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValorAprazo)
                    .addComponent(btnDesc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorAvista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValorAvista))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumeroParcelas)
                    .addComponent(btnNumeroParcelas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValorParcela))
                .addGap(37, 37, 37))
        );

        btnClonar.setText("Clonar");
        btnClonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClonarActionPerformed(evt);
            }
        });

        salvarButton.setText("Salvar");
        salvarButton.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                salvarButtonFocusLost(evt);
            }
        });
        salvarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvarButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Editar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblValidade.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblValidade.setText("Validade");

        lblQuantidadeEstoque.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblQuantidadeEstoque.setText("Qnt. em Estoque");

        txtQuantidade.setText("0");
        txtQuantidade.setEnabled(false);
        txtQuantidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQuantidadeFocusGained(evt);
            }
        });

        lblCor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCor.setText("Cor");

        btnCor.setText("i");
        btnCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCorActionPerformed(evt);
            }
        });

        lblTamanho.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblTamanho.setText("Tamanho");

        btnTamanho.setText("i");
        btnTamanho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTamanhoActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Código de Barras");

        txtCodigoBarras.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoBarrasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoBarrasFocusLost(evt);
            }
        });

        lblID.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblID.setText("ID");

        txtID.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(lblID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblValidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblQuantidadeEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTamanho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cmbCor, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtCodigoBarras, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTamanho, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtValidade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTamanho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCodigoBarras)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtQuantidade)
                    .addComponent(lblQuantidadeEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTamanho, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTamanho)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCor)
                    .addComponent(cmbCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelAdicionarImagem1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblFornencedor)
                            .addComponent(lblProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbProdutos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbFornecedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelAdicionarImagem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnClonar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(salvarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProduto)
                    .addComponent(cmbProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(cmbFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFornencedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(panelAdicionarImagem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClonar)
                            .addComponent(salvarButton)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

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

    private void btnClonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClonarActionPerformed
        if (salvarItemEstoque()) {
            itemEstoque = new ItemEstoque();
            String id = ""+EntityService.getLastIDplus1(ItemEstoque.class);
            txtID.setText(id);
//            txtValidade.setDate(Calendar.getInstance().getTime());
            if (txtCodigoBarras.getText().matches("\\d+")) {
                txtCodigoBarras.setText(geraProximoCodigoBarras());
            }
        }
    }//GEN-LAST:event_btnClonarActionPerformed

    String geraProximoCodigoBarras() {
        EntityManager em = JPA.getEM();
        String sCodigoBarras = (String) em.createQuery("SELECT max(e.codigoBarras) FROM ItemEstoque e")
                .getSingleResult();

        if (sCodigoBarras == null) {
            sCodigoBarras = "1";
        }
//        char[] dados = sCodigoBarras.toCharArray();
        int zerosAEsquesda = sCodigoBarras.length();
//        for (int i = 0; i < dados.length; i++) {
//            char dado = dados[i];
//            if (dado != '0'){
//                break;
//            }
//            zerosAEsquesda ++;
//        }
        int codigoBarras = Integer.valueOf(sCodigoBarras);
        String proximo = String.format("%0"+zerosAEsquesda+"d", codigoBarras +1);
        return proximo;
    }
    
    

    private void salvarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvarButtonActionPerformed
        
        if (salvarItemEstoque()){
            dispose();
        }


    }//GEN-LAST:event_salvarButtonActionPerformed

    private boolean salvarItemEstoque() throws HeadlessException {
        try {
            preecheItemEstoque(itemEstoque);
            
            EntityManager em = JPA.getEM();
            em.getTransaction().begin();
            
            this.itemEstoque = em.merge(itemEstoque);
            em.getTransaction().commit();
            em.close();
            
            System.out.println(itemEstoque);
        } catch (FormatacaoException | IOException ex) {
            Logger.getLogger(TelaItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void preecheItemEstoque(ItemEstoque item) throws IOException, FormatacaoException {
        item.setProduto((Produto) Formatador.notNull("Produto", (Produto) cmbProdutos.getSelectedItem()));
        item.setFornecedor((Empresa) cmbFornecedor.getSelectedItem());
        item.setCor((Cor) cmbCor.getSelectedItem());
        item.setTamanho( (Tamanho) cmbTamanho.getSelectedItem());
        item.setValidade(txtValidade.getDate());
        
        item.setQuantidade(Formatador.formatDouble(lblQuantidade.getText(), ""+parse(txtQnt.getText()).doubleValue()));
        item.setValorCusto(Formatador.formatPreco(lblCustoUN.getText(), txtCustoUN.getText()));
        
        item.setValorAprazo(Formatador.formatPreco(lblValorAprazo.getText(), txtValorAprazo.getText()));
        item.setValorAvista(Formatador.formatPreco(lblValorAvista.getText(), txtValorAvista.getText()));
        
        item.setNumeroParcelas(Formatador.formatDouble(lblNumeroParcelas.getText(), txtNumeroParcelas.getText()).intValue());
        item.setValorParcelaSugerida(Formatador.formatPreco(lblValorParcela.getText(), txtValorParcela.getText()));
        if (codigoBarrasExiste()) {
            throw new FormatacaoException("Este código de barras já existe");
        }
        item.setCodigoBarras(txtCodigoBarras.getText());
        
        item.setImagem(IMAGE_SERVICE.substituirImagem(panelAdicionarImagem1, item));
    }

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void txtQuantidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantidadeFocusGained
        txtQuantidade.selectAll();
    }//GEN-LAST:event_txtQuantidadeFocusGained

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        Produto produtoSelecionado = (Produto) cmbProdutos.getSelectedItem();

        TelaPesquisar telaProduto = new TelaPesquisar(new ProdutoTableModel(), custoOrdenado);
        telaProduto.setSelecionado(produtoSelecionado);
        Optional selecionado = telaProduto.getItemSelecionado();
        EntityManager em = JPA.getEM();
        TelaUtil.carregarObjetosNaComboBox(em, cmbProdutos, Produto.class);
        em.close();
        cmbProdutos.setSelectedItem(selecionado.orElse(produtoSelecionado));
    }//GEN-LAST:event_btnEditarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Empresa fornecedorSelecionado = (Empresa) cmbFornecedor.getSelectedItem();
        
        TelaPesquisar telaFornecedores  = new TelaPesquisar(new FornecedorTableModel(), false);
        telaFornecedores.setSelecionado(fornecedorSelecionado);
        Optional selecionado = telaFornecedores.getItemSelecionado();

        EntityManager em = JPA.getEM();
        TelaUtil.carregarEmpresaNaComboBox(em, cmbFornecedor, TipoEmpresa.FORNECEDOR);
        em.close();
        cmbFornecedor.setSelectedItem(selecionado.orElse(fornecedorSelecionado));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void salvarButtonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_salvarButtonFocusLost
    }//GEN-LAST:event_salvarButtonFocusLost

    private void cmbProdutosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbProdutosItemStateChanged
        Produto selecionado = (Produto) cmbProdutos.getSelectedItem();
        if (selecionado != null) {
            lblProduto.setText("Produto código " + selecionado.getId() + " -");

            EntityManager em = JPA.getEM();
            Double quantidade = (Double) em.createQuery("SELECT sum(e.quantidade) FROM ItemEstoque e WHERE e.deleted = FALSE and e.produto = :p")
                    .setParameter("p", selecionado)
                    .getSingleResult();
            if (quantidade == null) {
                quantidade = 0d;
            }
            quantidadeTotalOriginal = quantidade;
            txtQuantidade.setText(df.format(quantidade));

        } else {
            lblProduto.setText("Produto");
        }
    }//GEN-LAST:event_cmbProdutosItemStateChanged

    private void txtMargemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMargemFocusLost

    }//GEN-LAST:event_txtMargemFocusLost

    private void btnCalculaVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculaVendaActionPerformed
        atualizaViaMargem(txtMargem.getText());
    }//GEN-LAST:event_btnCalculaVendaActionPerformed

    private void btnNumeroParcelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumeroParcelasActionPerformed
        atualizaNumeroParcelas(txtNumeroParcelas.getText());
    }//GEN-LAST:event_btnNumeroParcelasActionPerformed

    private void txtMargemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMargemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMargemActionPerformed

    private void btnTamanhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTamanhoActionPerformed
        Class classe = Tamanho.class;
        Tamanho anterior = (Tamanho)  cmbTamanho.getSelectedItem();
        Optional<Tamanho> itemSelecionado = new TelaPesquisar(new ItemNomeavelTableModel( anterior, classe)).getItemSelecionado();
        EntityManager em = JPA.getEM();
        TelaUtil.carregarObjetosNaComboBox(em, cmbTamanho, Tamanho.class);
        em.close();
        
        cmbTamanho.setSelectedItem(itemSelecionado.orElse(anterior));

    }//GEN-LAST:event_btnTamanhoActionPerformed

    private void btnCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCorActionPerformed
        Class classe = Cor.class;
        Cor anterior = (Cor) cmbCor.getSelectedItem();
        Optional<Cor> itemSelecionado = new TelaPesquisar(new ItemNomeavelTableModel( anterior, classe)).getItemSelecionado();
        EntityManager em = JPA.getEM();
        TelaUtil.carregarObjetosNaComboBox(em, cmbCor, Cor.class);
        em.close();

        cmbCor.setSelectedItem(itemSelecionado.orElse(anterior));

    }//GEN-LAST:event_btnCorActionPerformed

    private void txtCodigoBarrasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoBarrasFocusGained
        txtCodigoBarras.setBackground(Color.WHITE);
        txtCodigoBarras.setToolTipText("");
    }//GEN-LAST:event_txtCodigoBarrasFocusGained

    private void txtCodigoBarrasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoBarrasFocusLost
        codigoBarrasExiste();
    }//GEN-LAST:event_txtCodigoBarrasFocusLost

    private void btnDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescActionPerformed
        String sValorAprazo = txtValorAprazo.getText();
        if (sValorAprazo.isEmpty()) {
            return;
        }
        BigDecimal valorAprazo = parse(sValorAprazo);
        txtValorAvista.setText(formata(valorAprazo.multiply(Regras.DESCONTO_A_VISTA.divide(new BigDecimal("100")))));
    }//GEN-LAST:event_btnDescActionPerformed

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        txtCustoTotal.setEnabled(jCheckBox1.isSelected());
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void txtCustoUNFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCustoUNFocusGained
        custoOrdenado = false;
    }//GEN-LAST:event_txtCustoUNFocusGained

    private void txtCustoTotalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCustoTotalFocusGained
        custoOrdenado = true;
    }//GEN-LAST:event_txtCustoTotalFocusGained

    String formata(String valor) {
        return formata(new BigDecimal(valor));
    }
    
    String formata(BigDecimal valor) {
        return df.format(valor.setScale(2, RoundingMode.HALF_UP));
    }
    
    private boolean codigoBarrasExiste() {
        if (!txtCodigoBarras.getText().isEmpty() && !txtCodigoBarras.getText().equals(itemEstoque.getCodigoBarras())) {
            boolean existe = !JPA.getEM()
                    .createQuery("SELECT e FROM ItemEstoque e WHERE e.codigoBarras = :codigo")
                    .setParameter("codigo", txtCodigoBarras.getText()).setMaxResults(1).getResultList().isEmpty();
            if (existe) {
                txtCodigoBarras.setBackground(Color.YELLOW);
                txtCodigoBarras.setToolTipText("Este código de barras já existe");
            }
            return existe;
        }
        return false;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalculaVenda;
    private javax.swing.JButton btnClonar;
    private javax.swing.JButton btnCor;
    private javax.swing.JButton btnDesc;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnNumeroParcelas;
    private javax.swing.JButton btnTamanho;
    private javax.swing.JComboBox<Cor> cmbCor;
    private javax.swing.JComboBox<Empresa> cmbFornecedor;
    private javax.swing.JComboBox<Produto> cmbProdutos;
    private javax.swing.JComboBox<Tamanho> cmbTamanho;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblCor;
    private javax.swing.JLabel lblCustoTotal;
    private javax.swing.JLabel lblCustoUN;
    private javax.swing.JLabel lblFornencedor;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblMargem;
    private javax.swing.JLabel lblNumeroParcelas;
    private javax.swing.JLabel lblProduto;
    private javax.swing.JLabel lblQuantidade;
    private javax.swing.JLabel lblQuantidadeEstoque;
    private javax.swing.JLabel lblTamanho;
    private javax.swing.JLabel lblValidade;
    private javax.swing.JLabel lblValorAprazo;
    private javax.swing.JLabel lblValorAvista;
    private javax.swing.JLabel lblValorParcela;
    private gerenciadordevendas.telas.PanelAdicionarImagem panelAdicionarImagem1;
    private javax.swing.JButton salvarButton;
    private javax.swing.JTextField txtCodigoBarras;
    private javax.swing.JTextField txtCustoTotal;
    private javax.swing.JTextField txtCustoUN;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMargem;
    private javax.swing.JTextField txtNumeroParcelas;
    private javax.swing.JTextField txtQnt;
    private javax.swing.JTextField txtQuantidade;
    private com.toedter.calendar.JDateChooser txtValidade;
    private javax.swing.JTextField txtValorAprazo;
    private javax.swing.JTextField txtValorAvista;
    private javax.swing.JTextField txtValorParcela;
    // End of variables declaration//GEN-END:variables

    private void atualizaCodigoBarras(String sCodigoBarras) {
        if (sCodigoBarras.isEmpty()) {
            return;
        }
        txtCodigoBarras.setBackground(Color.WHITE);
        EntityManager em = JPA.getEM();
        List codigos = em.createQuery("SELECT e.codigoBarras FROM ItemEstoque e WHERE e.deleted = FALSE and e.codigoBarras = :p")
                    .setParameter("p", sCodigoBarras)
                    .getResultList();
        em.close();
        if (!codigos.isEmpty()) {
            txtCodigoBarras.setBackground(Color.red);
            txtCodigoBarras.setToolTipText("Este código de barras já existe");
        }
    }
}
