/*
 * GerenciadorDeVendas: Fornecedor1.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 15:47:56
 */

package gerenciadordevendas.model

/**
 *
 * @author Ramon Porto
 */
@javax.persistence.Entity
class Fornecedor extends BaseEntity implements Comparable<Fornecedor>{

    String nome;
    String telefone;
    String vendedor;
    String telefoneVendedor;
    String observacoes;

    @Override
    String toString() { nome }

     @Override
    int compareTo(Fornecedor o) { nome <=> o.nome }
    
}

