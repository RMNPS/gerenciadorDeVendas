package gerenciadordevendas.conversao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * converte uma String com data em DD/MM/YY para Date de validade
 * @author ramon

 */
public class ConverteStringParaDataNascimento implements Conversao<String, Date> {
    
    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
    @Override
    public Date converte(String sValidade) throws ConversaoException{
        try {
            Date validade = format.parse(sValidade);
            return validade;
        } catch (ParseException ex) {
            throw new ConversaoException("Insira uma data válida no padrão DD/MM/AAAA");
        }
    }

    
    
}
