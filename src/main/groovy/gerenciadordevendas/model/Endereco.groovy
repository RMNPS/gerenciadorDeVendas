/*
 * GerenciadorDeVendas: Cliente1.groovy
 * Enconding: UTF-8
 * Data de criação: 18/09/2018 17:26:49
 */

package gerenciadordevendas.model

import java.text.Collator
import javax.persistence.Entity
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
class Endereco extends BaseEntity implements Comparable<Endereco> {
    
    private static final Collator collator = Collator.getInstance();
    
    String logradouro
    Integer numero
    String complemento
    String bairro
    String cidade
    String cep
    UF uf
    @ManyToOne
    @JoinColumn(name = "empresa")
    Empresa empresa
    @ManyToOne
    @JoinColumn(name = "cliente")
    Cliente cliente

    @Override
    String toString() { " - " + ", " + cep +  " - " + cidade + ", " + uf }

    @Override
    int compareTo(Endereco o) { 
        collator.setStrength(Collator.PRIMARY)
        collator.compare(toString(), o.toString())
    }
}