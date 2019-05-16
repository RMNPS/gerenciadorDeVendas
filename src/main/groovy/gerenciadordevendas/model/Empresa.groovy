/*
 * GerenciadorDeVendas: Cliente1.groovy
 * Enconding: UTF-8
 * Data de criação: 18/09/2018 17:26:49
 */

package gerenciadordevendas.model

import java.text.Collator
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.CascadeType

@Entity
class Empresa extends BaseEntity implements Comparable<Empresa>, EntidadeComImagem  {
    
    private static final Collator collator = Collator.getInstance()
    
    String nome
    String cnpj
    String inscricaoEstadual
    String inscricaoMunicipal
    @OneToMany(mappedBy = "empresa", cascade = [CascadeType.ALL])
    List<Endereco> enderecos
    String email
    String telefone
    String celular
    String observacoes
    String imagem
    TipoEmpresa tipoEmpresa

    @Override
    String toString() { nome }

    @Override
    int compareTo(Empresa o) {
        collator.strength = Collator.PRIMARY
        collator.compare(nome, o.nome)
    }
    
    void setEnderecos(List<Endereco> enderecos) {
        for (Endereco e: enderecos) {
            e.empresa = this
        }
        this.enderecos = enderecos
    }
}