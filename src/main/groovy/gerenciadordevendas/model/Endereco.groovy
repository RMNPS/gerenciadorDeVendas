
package gerenciadordevendas.model

import java.text.Collator
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

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
    @JoinColumn(name = "pessoaFisica")
    PessoaFisica pessoaFisica

    @Override
    String toString() { "${logradouro}, ${numero} - ${bairro}, ${cidade}, ${cep} - ${uf}" }

    @Override
    int compareTo(Endereco o) { 
        collator.setStrength(Collator.PRIMARY)
        collator.compare(toString(), o.toString())
    }
}