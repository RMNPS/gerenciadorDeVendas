/*
 * GerenciadorDeVendas: Conta1.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 16:05:53
 */

package gerenciadordevendas.model

import javax.persistence.EntityManager;
import gerenciadordevendas.JPA
import gerenciadordevendas.Regras
import gerenciadordevendas.avaliacao.AvaliadorConta
import gerenciadordevendas.exception.TransacaoException
import java.math.BigDecimal
import java.util.Collections
import java.util.Set
import java.util.TreeSet
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne

/**
 *
 * @author ramon
 */
@Entity
//@Customizer(ConfigureDeletedFilter.class)
class Conta extends BaseEntity {

    private static Conta padrao;

    @OneToOne
    @JoinColumn
    Cliente cliente;
    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
    TreeSet<Venda> vendas;

    @Column(precision = 12, scale = 3)
    BigDecimal saldo;
    @Column(precision = 12, scale = 3)
    BigDecimal limite;
    int prazo;

    protected Conta() {  }
    
    Conta(Cliente c) {
        cliente = c;
        vendas = new TreeSet<>();
        saldo = 0g;
        limite = BigDecimal.valueOf(Regras.LIMITE_PADRAO_CONTA);
        prazo = Regras.TEMPO_LIMITE_PAGAMENTO_CONTA;
    }


    Set<Venda> getVendas() {
        return Collections.unmodifiableSet(vendas);
    }

    void addVenda(Venda v) {
        if (vendas == null) {
            vendas = new TreeSet<>()
        }
        vendas << v;
        saldo = saldo + v.total;
    }

    void removeVenda(Venda v) {
        if (vendas.remove(v)) {
            saldo = saldo - v.total;
            v.deleted = true;
            if (saldo < 0g) {
                saldo = BigDecimal.ZERO;
            }
        } else
            throw new TransacaoException("Esta venda nunca foi informada ou já foi removida");
    }
 
    void remover(RegistroDeFluxo r) {
        if (r instanceof Venda) {
            removeVenda((Venda) r);
        } else {
            throw new TransacaoException("Este campo não pode ser removido");
        }
    }

    Set<EstadoContaFluxo> getEstadosAsRegistroDeFluxo() {
        Set<EstadoContaFluxo> result = new HashSet<>();
        Set<String> e = new AvaliadorConta().avaliar(this);
        for (String estado : e) {
            result << new EstadoContaFluxo(estado);
        }
        return result;
    }

    static final Conta padrao() {
        if (padrao == null) {
            
            EntityManager em = JPA.getEM();
            em.transaction.begin()
            padrao =  em.createQuery("SELECT f FROM Conta f WHERE f.uuid = :x AND f.deleted = FALSE", Conta.class)
            .setParameter("x", "1335a2f0-4aaa-401e-b2d4-02240a0474bf")
            .getResultStream().findAny().orElseGet{
                Conta padrao = new Conta()
                em.persist(padrao)
                padrao.limite = null;
                padrao.vendas = new TreeSet<>();
                padrao.saldo = 0g;
                padrao.setUuid("1335a2f0-4aaa-401e-b2d4-02240a0474bf");

                em.merge(padrao);
            }
            em.transaction.commit();
            em.close();
        }
        return padrao;
    }
}

