/*
 * GerenciadorDeVendas: TesteDatas.java
 * Enconding: UTF-8
 * Data de criação: 24/05/2018 16:39:12
 */
package gerenciadordevendas;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Ramon Porto
 */
public class TesteDatas {
    public static void main(String[] args) {
        System.out.println("Passou = " + jaPassouTrintaDias(new Date(2018-1900, Calendar.APRIL, 23)));
    }
    
    private static boolean jaPassouTrintaDias(Date data){
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DAY_OF_MONTH, 30);  
        cal.set(Calendar.HOUR_OF_DAY, 23);  
        cal.set(Calendar.MINUTE, 59);  
        cal.set(Calendar.SECOND, 59);  
        cal.set(Calendar.MILLISECOND, 999);  
        Date dataMaisTrinta = cal.getTime();
        Date hoje = Calendar.getInstance().getTime();
        return dataMaisTrinta.before(hoje);
    }
}
