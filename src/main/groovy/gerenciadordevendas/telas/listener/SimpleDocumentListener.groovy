/*
 * GerenciadorDeVendas: SimpleDocumentListener.java
 * Enconding: UTF-8
 * Data de criação: 22/02/2018 15:49:27
 */
package gerenciadordevendas.telas.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Ramon Porto
 */
abstract class SimpleDocumentListener implements DocumentListener{

    @Override
    abstract void insertUpdate(DocumentEvent e);

    @Override
    void removeUpdate(DocumentEvent e) {   }

    @Override
    void changedUpdate(DocumentEvent e) {   }
    
}
