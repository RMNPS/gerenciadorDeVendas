package gerenciadordevendas.model

import javax.persistence.Entity

@Entity
class Usuario extends BaseEntity {

    PessoaFisica pessoaFisica
    String login
    String senha


}
