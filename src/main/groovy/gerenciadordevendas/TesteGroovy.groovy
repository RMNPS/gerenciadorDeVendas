/*
 * GerenciadorDeVendas: TesteGroovy.groovy
 * Enconding: UTF-8
 * Data de criação: 28/09/2018 13:59:03
 */

package gerenciadordevendas

import gerenciadordevendas.model.Conta
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Ramon Porto
 */
class TesteGroovy {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            println(RandomStringUtils.randomAlphanumeric(10));
        }
        
    }
}

