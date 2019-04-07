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
        
    
    String nome
    @Temporal(TemporalType.DATE)
    Date dataNasc
    String cpf
    String rg
    String ufRg
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    Conta conta
    String endereco
    String bairro
    String cidade
    String cep
    UF uf
    String telefone
    String celular
    String observacoes
    @OneToMany(mappedBy = "cliente")
    Set<ClienteAutorizado> autorizados

    Cliente() {  conta = new Conta(this) }

    static Cliente padrao() {
        Cliente padrao = new Cliente(
            id: 1, 
            nome: "Cliente Visitante", 
            dataNasc: Calendar.instance.time
        )
        Conta contaPadrao = Conta.padrao()
        contaPadrao.cliente = padrao
        padrao.conta = contaPadrao
        return padrao
    }

    @Override
    String toString() { nome }

    @Override
    int compareTo(Cliente o) { 
        collator.setStrength(Collator.PRIMARY);
        collator.compare(nome, o.nome);
    }
}


