package gerenciadordevendas.exception;

/**
 *
 * @author Ramon Porto
 */
class FormatacaoException extends RuntimeException {

    FormatacaoException(String message) {
        super(message)
    }

    FormatacaoException(String message, Throwable cause) {
        super(message, cause)
    }

    FormatacaoException(Throwable cause) {
        super(cause)
    }
    
}
