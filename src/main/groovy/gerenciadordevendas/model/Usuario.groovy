package gerenciadordevendas.model

import javax.persistence.Entity

@Entity
class Usuario extends BaseEntity {

    PessoaFisica pessoaFisica
    Empresa empresa
    String login
    String senha


}
