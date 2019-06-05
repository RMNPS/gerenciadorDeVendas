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
//@NamedQuery(name = "Cliente.buscarTodos", query = "SELECT e FROM Cliente e")
@Entity
class Cliente extends BaseEntity implements Comparable<Cliente> {
    
    private static final Collator collator = Collator.getInstance();
        
    PessoaFisica pessoaFisica
    Empresa empresa
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    Conta conta
    String observacoes
    @OneToMany(mappedBy = "cliente")
    Set<ClienteAutorizado> autorizados

    Cliente() {  conta = new Conta(this) }

    @Override
    String toString() {
        if (pessoaFisica) {
            return pessoaFisica.nome
        }
        if (empresa) {
            return empresa.nome
        }
        return super.toString()
    }

    @Override
    int compareTo(Cliente o) { 
        collator.setStrength(Collator.PRIMARY);
        collator.compare(nome, o.nome);
    }

    String getNome() {
        if (pessoaFisica) {
            pessoaFisica.nome
        } else {
            empresa.nome
        }
    }
    
    String getCPFouCNPJ() {
        if (pessoaFisica) {
            pessoaFisica.cpf
        } else {
            empresa.cnpj
        }
    }
}


