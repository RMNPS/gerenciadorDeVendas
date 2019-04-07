/*
 * GerenciadorDeVendas: TelaDados.java
 * Enconding: UTF-8
 * Data de criação: 19/02/2018 17:23:25
 */
package gerenciadordevendas.telas;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.Estado;
import gerenciadordevendas.model.Venda;
import gerenciadordevendas.tablemodel.EstoqueAbcTableModel;
import java.awt.Dialog;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ramon Porto
 */
public class TelaDados extends javax.swing.JDialog {

    private static final long DIA_COMPLETO = 24l * 60l * 60l * 1000l;
    private static final long MILLIS_IN_30_DAYS = 30l * 24l * 60l * 60l * 1000l;
    private final EstoqueAbcTableModel model = new EstoqueAbcTableModel();

    public TelaDados() {
        super(null, Dialog.DEFAULT_MODALITY_TYPE);
        initComponents();
        model.setJTable(tabela);

        long dateMenos30 = (fim.getDate().getTime() - MILLIS_IN_30_DAYS);
        inicio.setDate(new Date(dateMenos30));

        model.carregar(inicio.getDate(), fim.getDate());

        calculaReceitas();
        calcularFeria();
    }

    private void calculaReceitas() {
        EntityManager em = JPA.getEM();
        TypedQuery<BigDecimal> query = em.createQuery("SELECT sum(v.total) from Venda v "
                + "where v.estado = :estado and v.dataCriacao between :inicio and :fim", BigDecimal.class);
        query.setParameter("estado", Estado.PAGA);
        query.setParameter("inicio", inicio.getDate());
        query.setParameter("fim", new Date(fim.getDate().getTime() + DIA_COMPLETO));

        BigDecimal receita = Optional.ofNullable(query.getSingleResult()).orElse(BigDecimal.ZERO);
        txtReceitaPaga.setText(receita.setScale(2).toString());

        query.setParameter("estado", Estado.EM_CONTA);

        BigDecimal receitaEmConta = Optional.ofNullable(query.getSingleResult()).orElse(BigDecimal.ZERO);
        em.close();

        txtReceitaContas.setText(receitaEmConta.setScale(2).toString());

        txtReceitaTotal.setText(receita.add(receitaEmConta).setScale(2).toString());
    }

    private void calcularFeriaMedia() {
        EntityManager em = JPA.getEM();
        TypedQuery<BigDecimal> query = em.createQuery("SELECT sum(v.total) from Venda v "
                + "WHERE v.dataCriacao >= :dataInicio AND v.dataCriacao < :dataFim AND v.estado = :estado", BigDecimal.class)
                .setParameter("dataInicio", inicio.getDate(), TemporalType.DATE)
                .setParameter("dataFim", new Date(fim.getDate().getTime() + DIA_COMPLETO), TemporalType.DATE)
                .setParameter("estado", Estado.PAGA);

        BigDecimal feriasNoPeriodo = Optional.ofNullable(query.getSingleResult())
                .orElse(BigDecimal.ZERO);

        long dt = (fim.getDate().getTime() - inicio.getDate().getTime()) + 3600000L;
        long dias = (dt / 86400000L);
        if (dias == 0) {
            dias = 1;
        }
        txtFeriaMedia.setText(feriasNoPeriodo.divide(BigDecimal.valueOf(dias), new MathContext(2)).toString());
    }

    private void calcularFeria() {
        EntityManager em = JPA.getEM();
        TypedQuery<BigDecimal> query = em.createQuery("SELECT sum(v.total) from Venda v "
                + "WHERE v.dataCriacao >= :dataInicio AND v.dataCriacao < :dataFim AND v.estado = :estado",
                BigDecimal.class);

        query.setParameter("dataInicio", dataFeria.getDate(), TemporalType.DATE);
        query.setParameter("dataFim", new Date(dataFeria.getDate().getTime() + DIA_COMPLETO), TemporalType.DATE);
        query.setParameter("estado", Estado.PAGA);

        BigDecimal feria = Optional.ofNullable(query.getSingleResult()).orElse(BigDecimal.ZERO);
        txtFeria.setText(feria.setScale(2).toString());

        BigDecimal feriaEmConta = (BigDecimal) query.setParameter("estado", Estado.EM_CONTA).getSingleResult();
        
        feriaEmConta = Optional.ofNullable(feriaEmConta).orElse(BigDecimal.ZERO);
        txtFeriaVendasEmConta.setText(feriaEmConta.setScale(2).toString());

        txtFeriaTotal.setText(feria.add(feriaEmConta).setScale(2).toString());
        em.close();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        inicio = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fim = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        lblReceita = new javax.swing.JLabel();
        txtReceitaPaga = new javax.swing.JTextField();
        txtReceitaContas = new javax.swing.JTextField();
        lblReceita1 = new javax.swing.JLabel();
        lblReceita2 = new javax.swing.JLabel();
        txtReceitaTotal = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtFeria = new javax.swing.JTextField();
        txtFeriaVendasEmConta = new javax.swing.JTextField();
        lblReceita4 = new javax.swing.JLabel();
        lblReceita5 = new javax.swing.JLabel();
        txtFeriaTotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dataFeria = new com.toedter.calendar.JDateChooser();
        lblReceita6 = new javax.swing.JLabel();
        txtFeriaMedia = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dados do Negócio");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("./icons/briefcase-outline.png")));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabela);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        inicio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                inicioPropertyChange(evt);
            }
        });

        jLabel1.setText("Período de");

        jLabel3.setText("à");

        fim.setDate(new Date());
        fim.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fimPropertyChange(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Receita no período"));

        lblReceita.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReceita.setText("Valores pagos R$");

        txtReceitaPaga.setEditable(false);

        txtReceitaContas.setEditable(false);

        lblReceita1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReceita1.setText("Valores em conta R$");

        lblReceita2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReceita2.setText("Valores pagos + em conta R$");

        txtReceitaTotal.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblReceita2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblReceita1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblReceita, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtReceitaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReceitaContas, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReceitaPaga, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReceita)
                    .addComponent(txtReceitaPaga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReceita1)
                    .addComponent(txtReceitaContas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReceita2)
                    .addComponent(txtReceitaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Féria"));

        txtFeria.setEditable(false);

        txtFeriaVendasEmConta.setEditable(false);

        lblReceita4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReceita4.setText("Valores em conta R$");

        lblReceita5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReceita5.setText("Valores pagos + em conta R$");

        txtFeriaTotal.setEditable(false);

        jLabel4.setText("De");

        dataFeria.setDate(new Date());
        dataFeria.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dataFeriaPropertyChange(evt);
            }
        });

        lblReceita6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReceita6.setText("Féria média no período R$ ");

        txtFeriaMedia.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dataFeria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblReceita5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblReceita4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFeriaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFeriaVendasEmConta, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFeria, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(lblReceita6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFeriaMedia, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFeria, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dataFeria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReceita4)
                    .addComponent(txtFeriaVendasEmConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReceita5)
                    .addComponent(txtFeriaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReceita6)
                    .addComponent(txtFeriaMedia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void inicioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_inicioPropertyChange
        if ("date".equals(evt.getPropertyName())) {
            model.carregar(inicio.getDate(), new Date(fim.getDate().getTime() + DIA_COMPLETO));
            calculaReceitas();
            calcularFeriaMedia();
        }
    }//GEN-LAST:event_inicioPropertyChange

    private void fimPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fimPropertyChange
        inicioPropertyChange(evt);
    }//GEN-LAST:event_fimPropertyChange

    private void dataFeriaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dataFeriaPropertyChange
        if ("date".equals(evt.getPropertyName())) {
            calcularFeria();
        }
    }//GEN-LAST:event_dataFeriaPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser dataFeria;
    private com.toedter.calendar.JDateChooser fim;
    private com.toedter.calendar.JDateChooser inicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblReceita;
    private javax.swing.JLabel lblReceita1;
    private javax.swing.JLabel lblReceita2;
    private javax.swing.JLabel lblReceita4;
    private javax.swing.JLabel lblReceita5;
    private javax.swing.JLabel lblReceita6;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtFeria;
    private javax.swing.JTextField txtFeriaMedia;
    private javax.swing.JTextField txtFeriaTotal;
    private javax.swing.JTextField txtFeriaVendasEmConta;
    private javax.swing.JTextField txtReceitaContas;
    private javax.swing.JTextField txtReceitaPaga;
    private javax.swing.JTextField txtReceitaTotal;
    // End of variables declaration//GEN-END:variables
}
