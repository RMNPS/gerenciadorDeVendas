package gerenciadordevendas.telas;

import gerenciadordevendas.model.PessoaFisica;
import java.awt.Window;

public class TelaCliente extends javax.swing.JDialog {

    public TelaCliente(Window parent, PessoaFisica pessoaFisica) {
        super(parent, java.awt.Dialog.DEFAULT_MODALITY_TYPE);
        initComponents();
        panelPessoaFisica.setPessoaFisica(pessoaFisica);
    }

    public PessoaFisica getPessoaFisica() {
        return panelPessoaFisica.getPessoaFisica();
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        panelPessoaFisica.setPessoaFisica(pessoaFisica);
    }

    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPessoaFisica = new gerenciadordevendas.telas.PanelPessoaFisica();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Cliente");
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPessoaFisica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelPessoaFisica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gerenciadordevendas.telas.PanelPessoaFisica panelPessoaFisica;
    // End of variables declaration//GEN-END:variables
}
