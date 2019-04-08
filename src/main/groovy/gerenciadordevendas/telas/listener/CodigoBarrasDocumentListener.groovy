package gerenciadordevendas.telas.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

class CodigoBarrasDocumentListener implements DocumentListener {
    
    final JTextField textField;

    final DocumentAtualizavel da;

    
    CodigoBarrasDocumentListener(JTextField txtCustoTotal, DocumentAtualizavel da) {
        this.textField = txtCustoTotal;
        this.da = da;
    }

    void inicializa() {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.getDocument().addDocumentListener(CodigoBarrasDocumentListener.this);
                textField.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.getDocument().removeDocumentListener(CodigoBarrasDocumentListener.this);
            }
        });
    }

    @Override
    void insertUpdate(DocumentEvent e) {
        SwingUtilities.invokeLater({ ->  atualiza(e); });
    }

    private void atualiza(DocumentEvent e) {
        try {
            Document document = e.getDocument();
            String text = document.getText(0, document.getLength());

            if (text.isEmpty()) {
                da?.atualiza(text);
            } else {
                document.remove(e.getOffset(), e.getLength());
            }

        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    void removeUpdate(DocumentEvent e) {
        atualiza(e);
    }

    @Override
    void changedUpdate(DocumentEvent e) {
        atualiza(e);
    }

}
