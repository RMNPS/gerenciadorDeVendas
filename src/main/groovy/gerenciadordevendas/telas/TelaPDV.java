package gerenciadordevendas.telas;

import gerenciadordevendas.JPA;
import gerenciadordevendas.Regras;
import gerenciadordevendas.model.ItemEstoque;
import gerenciadordevendas.tablemodel.ProdutosTableModel;
import gerenciadordevendas.tablemodel.ItemVendaTableModel;
import gerenciadordevendas.model.ItemVenda;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class TelaPDV extends javax.swing.JFrame {

    private static final String ZERO_REAIS = "R$ 0,00";
    private static final SimpleDateFormat HORA_FORMAT = new SimpleDateFormat("E, dd 'de' MMMMM 'de' YYYY, HH:mm");
    private static final NumberFormat MONEY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private String codigo = "";
    private int count = 0;
    private long tempoUltima = 0;

    private final ItemVendaTableModel model = new ItemVendaTableModel();
    private String descAux = null;

    public TelaPDV() {
        initComponents();
        iniciarRelogio();
        model.setJTable(produtosVendaTable);
        numVendaLabel.setText("" + JPA.getEM().createQuery("SELECT count(e) FROM Venda e").getFirstResult());

        panelPrincipal.requestFocus();

    }

    private void limpaCampos() {
        descricaoLabel.setText("Adicione um item para iniciar as vendas");
        qntLabel.setText("0");
        valorUnLabel.setText(ZERO_REAIS);
        subTotalLabel.setText(ZERO_REAIS);
        totalLabel.setText(ZERO_REAIS);
        numVendaLabel.setText("" + JPA.getEM().createQuery("SELECT count(e) FROM Venda e").getFirstResult());
    }



    private void iniciarRelogio() {
        SwingUtilities.invokeLater(() -> new Thread(() -> {
            while (true) {
                try {
                    dataLabel.setText(HORA_FORMAT.format(new Date()));
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException("Ocorreu um erro ao atualizar a hora", ex);
                }
            }
        }).start());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        dataLabel = new javax.swing.JLabel();
        qntLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        produtosVendaTable = new javax.swing.JTable();
        descricaoLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        subTotalLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        valorUnLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        totalLabel = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        numVendaLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        vendedorLabel = new javax.swing.JLabel();
        lblComandos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        panelPrincipal.setBackground(new java.awt.Color(153, 153, 153));
        panelPrincipal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                panelPrincipalKeyPressed(evt);
            }
        });

        dataLabel.setFont(new java.awt.Font("Noto Sans", 0, 16)); // NOI18N
        dataLabel.setForeground(java.awt.Color.white);
        dataLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dataLabel.setText("data");

        qntLabel.setFont(new java.awt.Font("Noto Sans", 0, 22)); // NOI18N
        qntLabel.setForeground(java.awt.Color.white);
        qntLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        qntLabel.setText("0");

        produtosVendaTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        produtosVendaTable.setModel(model);
        produtosVendaTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                produtosVendaTableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(produtosVendaTable);

        descricaoLabel.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        descricaoLabel.setForeground(java.awt.Color.white);
        descricaoLabel.setText("Adicione um item para iniciar as vendas");

        jLabel3.setFont(new java.awt.Font("Noto Sans", 0, 22)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setText("x");

        subTotalLabel.setFont(new java.awt.Font("Noto Sans", 0, 28)); // NOI18N
        subTotalLabel.setForeground(java.awt.Color.white);
        subTotalLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        subTotalLabel.setText("R$ 0,00");

        valorUnLabel.setFont(new java.awt.Font("Noto Sans", 0, 22)); // NOI18N
        valorUnLabel.setForeground(java.awt.Color.white);
        valorUnLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        valorUnLabel.setText("R$ 0,00");

        jLabel6.setFont(new java.awt.Font("Noto Sans", 0, 16)); // NOI18N
        jLabel6.setForeground(java.awt.Color.white);
        jLabel6.setText("Subtotal");

        jLabel7.setFont(new java.awt.Font("Noto Sans", 0, 16)); // NOI18N
        jLabel7.setForeground(java.awt.Color.white);
        jLabel7.setText("Total");

        totalLabel.setFont(new java.awt.Font("Noto Sans", 1, 48)); // NOI18N
        totalLabel.setForeground(java.awt.Color.white);
        totalLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalLabel.setText("R$ 0,00");

        numVendaLabel.setForeground(java.awt.Color.white);
        numVendaLabel.setText("0");

        jLabel10.setForeground(java.awt.Color.white);
        jLabel10.setText("Venda");

        jLabel11.setForeground(java.awt.Color.white);
        jLabel11.setText("Operador");

        vendedorLabel.setForeground(java.awt.Color.white);
        vendedorLabel.setText("Padrão");

        lblComandos.setForeground(java.awt.Color.white);
        lblComandos.setText("F1 - Confirmar Venda | F2 - Inserir itemEstoque | F3 - Remover Produto");

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(descricaoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(dataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPrincipalLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numVendaLabel)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(vendedorLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 560, Short.MAX_VALUE)
                                .addComponent(lblComandos))
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(18, 18, 18)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(subTotalLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                                .addComponent(qntLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(valorUnLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(25, 25, 25))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dataLabel)
                .addGap(28, 28, 28)
                .addComponent(descricaoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(valorUnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(qntLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(subTotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(vendedorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(numVendaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblComandos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        System.out.println("keyPressed");
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        System.out.println("keyReased");
    }//GEN-LAST:event_formKeyReleased

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        System.out.println("keytyped");
    }//GEN-LAST:event_formKeyTyped

    private void panelPrincipalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panelPrincipalKeyPressed
        long tempoEntre = System.currentTimeMillis() - tempoUltima;

        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            if (!model.getTransacoes().isEmpty()) {
                finalizarVenda();
            } else {
                JOptionPane.showMessageDialog(this, "Adicione pelo menos um item para finalizar a venda");
            }

        } else if (evt.getKeyCode() == KeyEvent.VK_F2) {
            inserirProduto();

        } else if (evt.getKeyCode() == KeyEvent.VK_F3) {
            removerProduto();
            
        } else if (count == 0 || tempoEntre < 200) {

            if (evt.getKeyCode() == KeyEvent.VK_ENTER && tempoEntre < 100 && codigo.length() > 11) {
                introduzirProduto(codigo);
                count = 0;
                codigo = "";
            } else {

                tempoUltima = System.currentTimeMillis();
                codigo += evt.getKeyChar();
                count++;
            }
        } else {
            count = 0;
            codigo = "";
        }
    }//GEN-LAST:event_panelPrincipalKeyPressed


    private void finalizarVenda() {
        new TelaPagamento(model.getVenda()).setVisible(true);
        model.finalizarVenda();
        limpaCampos();
    }

    private void introduzirProduto(ItemVenda iv) {
        ItemVenda item = model.add(iv);
        preencherTelaProduto(item);
    }

    private void introduzirProduto(String codigo) {
        try {
            ItemVenda item = model.add(codigo);
            preencherTelaProduto(item);
        } catch (NoResultException e) {
            JOptionPane.showMessageDialog(this, "Produto não encontrado", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherTelaProduto(ItemVenda item) {
        qntLabel.setText("" + item.getQuantidade());
        descricaoLabel.setText(item.getItemEstoque().getProduto().getNome());
        valorUnLabel.setText(MONEY_FORMAT.format(item.getItemEstoque().getValorAprazo()));
        subTotalLabel.setText(MONEY_FORMAT.format(item.getSaldoVenda()));
        totalLabel.setText(MONEY_FORMAT.format(model.getTotal()));
    }

    private void removerProduto() {
        if (model.getRowCount() > 0) {
            produtosVendaTable.requestFocus();
            produtosVendaTable.setRowSelectionInterval(0, 0);
            descAux = descricaoLabel.getText();
            descricaoLabel.setText("F3 - Modo de Remoção");
        }
    }

    private void inserirProduto() {
        Optional<ItemEstoque> ic = new TelaPesquisar(new ProdutosTableModel()).getItemSelecionado();
        if (!ic.isPresent()) {
            return;
        }
        String erro = "";
        while (true) {
            String sQnt = JOptionPane.showInputDialog(erro + "Insira a quantidade", 1);
            if (sQnt == null) {
                return;
            }
            try {
                int qnt = Integer.valueOf(sQnt);
                if (qnt < 1 || qnt > Regras.QUANTIDADE_MAXIMA_PRODUTO) {
                    erro = "A quantidade deve estar entre 1 e " + Regras.QUANTIDADE_MAXIMA_PRODUTO + ".\n";
                    continue;
                }
                ItemVenda iv =  ItemVenda.Build(ic.get(), qnt);
                introduzirProduto(iv);
            } catch (NumberFormatException e) {
                erro = "A quantidade deve estar entre 1 e " + Regras.QUANTIDADE_MAXIMA_PRODUTO + ".\n";
            }
        }
    }

    

    private void produtosVendaTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_produtosVendaTableKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            if (produtosVendaTable.getSelectedRow() >= 0) {
                int[] selectedRows = produtosVendaTable.getSelectedRows();
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    model.remove(selectedRows[i]);
                }

                descricaoLabel.setText("        ");
                qntLabel.setText("0");
                valorUnLabel.setText(ZERO_REAIS);
                subTotalLabel.setText(ZERO_REAIS);
                totalLabel.setText(MONEY_FORMAT.format(model.getTotal()));

            } else {
                descricaoLabel.setText(descAux);
                descAux = null;
            }
            panelPrincipal.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_F3) { //Quando finalizada a remoção
            produtosVendaTable.clearSelection();
            panelPrincipal.requestFocus();
            descricaoLabel.setText(descAux);
            descAux = null;
        }
    }//GEN-LAST:event_produtosVendaTableKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dataLabel;
    private javax.swing.JLabel descricaoLabel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblComandos;
    private javax.swing.JLabel numVendaLabel;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTable produtosVendaTable;
    private javax.swing.JLabel qntLabel;
    private javax.swing.JLabel subTotalLabel;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JLabel valorUnLabel;
    private javax.swing.JLabel vendedorLabel;
    // End of variables declaration//GEN-END:variables
}
