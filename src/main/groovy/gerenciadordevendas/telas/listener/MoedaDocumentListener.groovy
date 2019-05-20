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

class MoedaDocumentListener implements DocumentListener {

    static final Pattern DIGITO_DECIMAL = Pattern.compile("\\d{0,10}(\\,\\d{0,2})?");
    final JTextField textField;

    final DocumentAtualizavel da;

    MoedaDocumentListener(JTextField txtCustoTotal) {
        this.textField = txtCustoTotal;
    }
    
    MoedaDocumentListener(JTextField txtCustoTotal, DocumentAtualizavel da) {
        this.textField = txtCustoTotal;
        this.da = da;
    }

    void inicializa() {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.getDocument().addDocumentListener(MoedaDocumentListener.this);
                textField.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.getDocument().removeDocumentListener(MoedaDocumentListener.this);
            }
        });
    }

    @Override
    void insertUpdate(DocumentEvent e) {
        SwingUtilities.invokeLater({  atualiza(e)});
    }

    private void atualiza(DocumentEvent e) {
        try {
            Document document = e.getDocument();
            String text = document.getText(0, document.getLength());

            if (text.isEmpty() || DIGITO_DECIMAL.matcher(text).matches()) {
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
//        atualiza(e);
        SwingUtilities.invokeLater({  atualiza(e)});
    }

    @Override
    void changedUpdate(DocumentEvent e) {
        atualiza(e);
    }

}
