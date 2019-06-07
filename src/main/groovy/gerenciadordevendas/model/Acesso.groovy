/*
 * GerenciadorDeVendas: Acesso.groovy
 * Enconding: UTF-8
 * Data de criação: 07/06/2019 17:05:58
 */

package gerenciadordevendas.model

import javax.persistence.Entity

/**
 *
 * @author Ramon Porto
 */
@Entity
class Acesso extends BaseEntity{
    String tela
    String campo
    int nivel
}

