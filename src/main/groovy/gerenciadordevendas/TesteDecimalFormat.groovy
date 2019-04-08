/*
 * GerenciadorDeVendas: TesteDecimalFormat.java
 * Enconding: UTF-8
 * Data de criação: 19/04/2018 13:51:59
 */
package gerenciadordevendas;

import java.text.DecimalFormat;

/**
 *
 * @author Ramon Porto
 */
public class TesteDecimalFormat {
    public static void main(String[] args) {
        DecimalFormat formatoMoeda = new DecimalFormat("+###,###,###,###,###,##0.00;-#");
        Float a = -1000000000.32f;
        System.out.println(formatoMoeda.format(a));
    }
}
