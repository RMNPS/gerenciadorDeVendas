/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciadordevendas.conversao;


/**
 *
 * @author ramon
 */
public class ConversaoException extends RuntimeException {

    public ConversaoException(String mensagem) {
        super(mensagem);
    }

    public ConversaoException(Throwable cause) {
        super(cause);
    }
    
    
    
}
