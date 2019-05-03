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
        def colunas = [ "Nome", "Sobrenome"];
        switch(colunas[1]) {
            case "Nome": println "Nome1111"; break;
            case "Sobrenome" : println "Sobrenome111"; break;
        }
        
    }
}

