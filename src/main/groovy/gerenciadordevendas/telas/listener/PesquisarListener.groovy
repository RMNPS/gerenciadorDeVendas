
package gerenciadordevendas.telas.listener;

import gerenciadordevendas.tablemodel.Pesquisavel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author Ramon Porto
 */
class PesquisarListener implements DocumentListener {

    final Pesquisavel p;

    PesquisarListener(Pesquisavel p) {
        this.p = p;
    }

    @Override
    void insertUpdate(DocumentEvent e) {
        pesquisar(e);
    }

    void pesquisar(DocumentEvent e) throws RuntimeException {
        try {
            Document document = e.getDocument();
            String texto = document.getText(0, document.getLength());
            p.pesquisar(texto);
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    void removeUpdate(DocumentEvent e) {
        pesquisar(e);
    }

    @Override
    void changedUpdate(DocumentEvent e) {
        pesquisar(e);
    }

}
