/*
 * GerenciadorDeVendas: TransacaoException.java
 * Enconding: UTF-8
 * Data de criação: 21/02/2018 13:38:36
 */
package gerenciadordevendas.exception;

/**
 *
 * @author Ramon Porto
 */
class TransacaoException extends RuntimeException{

    TransacaoException(String message) {
        super(message);
    }

    TransacaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransacaoException(Throwable cause) {
        super(cause);
    }
}
