package gerenciadordevendas.model;

@javax.persistence.Entity
class Vendedor extends Usuario implements Comparable<Vendedor>{

    @Override
    public String toString() {
        return  pessoaFisica?.nome ;
    }
    
    @Override
    int compareTo(Vendedor o) { return pessoaFisica.nome.compareTo(o.pessoaFisica.nome); };
}
