package gerenciadordevendas.model

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Caixa extends BaseEntity{

    @Column(precision = 12, scale = 2)
    BigDecimal saldoInicial
    List<Venda> vendas
}
