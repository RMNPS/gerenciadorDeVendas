/*
 * GerenciadorDeVendas: FormaPagamento.groovy
 * Enconding: UTF-8
 * Data de criação: 20/03/2019 14:57
 */

package gerenciadordevendas.model

import java.text.Collator
import javax.persistence.Column
import javax.persistence.Entity

/**
 *
 * @author Ramon Porto
 */
@Entity
class FormaPagamento extends BaseEntity implements Comparable<FormaPagamento>{
    
    private static final Collator collator = Collator.getInstance();
    
    String descricao
    @Column(precision = 12, scale = 2)
    BigDecimal taxa
    @Column(precision = 12, scale = 2)
    BigDecimal taxaMensal
  
    String toString() {
        return descricao
    }
    
    @Override
    int compareTo(FormaPagamento o) {
        collator.setStrength(Collator.PRIMARY)
        collator.compare(descricao, o.descricao)
    }
    
}

