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

/**
 *
 * @author Ramon Porto
 */
@Entity
class PessoaFisica extends BaseEntity implements Comparable<PessoaFisica>, EntidadeComImagem {
    
    private static final Collator collator = Collator.getInstance();

    String nome
    @Temporal(TemporalType.DATE)
    Date dataNasc
    String cpf
    String rg
    String ufRg
    String telefone
    String celular
    String imagem
    @OneToMany(mappedBy = "pessoaFisica", cascade = CascadeType.ALL)
    List<Endereco> enderecos
    String email

    @Override
    String toString() { nome }

    @Override
    int compareTo(PessoaFisica o) { 
        collator.setStrength(Collator.PRIMARY);
        collator.compare(nome, o.nome);
    }
    
    void setEnderecos(List<Endereco> enderecos) {
        for (Endereco e: enderecos) {
            e.pessoaFisica = this
        }
        this.enderecos = enderecos
    }
}


