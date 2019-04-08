/*
 * GerenciadorDeVendas: TextoListener.java
 * Enconding: UTF-8
 * Data de criação: 22/02/2018 15:28:12
 */
package gerenciadordevendas.telas.listener;

import java.util.regex.Pattern;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author Ramon Porto
 */
class TextoListener extends SimpleDocumentListener {

    static final Pattern LETRAS = Pattern.compile('^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$');
    final int maxCharacters;

    TextoListener(int maxCharacters) {
        this.maxCharacters = maxCharacters;
    }

    @Override
    void insertUpdate(DocumentEvent e) {
        SwingUtilities.invokeLater({ ->
            try {
                Document document = e.getDocument();
                String text = document.getText(0, document.getLength());

                if (text.length() > maxCharacters || !LETRAS.matcher(text).matches()) {
                    document.remove(e.getOffset(), e.getLength());
                }

            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
