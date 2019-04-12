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
class Empresa extends BaseEntity implements Comparable<Empresa> {
    
    private static final Collator collator = Collator.getInstance();
    
    String nome
    String cnpj
    String inscricaoEstadual
    String inscricaoMunicipal
    @OneToMany(mappedBy = "empresa")
    List<Endereco> enderecos
    String telefone
    String celular
    String observacoes
    String caminhoLogotipo

    @Override
    String toString() { nome }

    @Override
    int compareTo(Empresa o) { 
        collator.setStrength(Collator.PRIMARY)
        collator.compare(nome, o.nome)
    }
}