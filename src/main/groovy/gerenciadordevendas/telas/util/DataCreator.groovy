/*
 * GerenciadorDeVendas: DataCreator.java
 * Enconding: UTF-8
 * Data de criação: 20/03/2019 15:31:16
 */
package gerenciadordevendas.telas.util;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.FormaPagamento
import gerenciadordevendas.model.Vendedor
import javax.persistence.EntityManager
import java.util.Optional;
import javax.persistence.Query;

/**
 *
 * @author Ramon Porto
 */
public class DataCreator {
    void create() {
        EntityManager em = JPA.getEM()
        em.transaction.begin()
        FormaPagamento forma
        Query query = em.createQuery("SELECT f FROM FormaPagamento f WHERE f.uuid = :x AND f.deleted = FALSE");
        
        query.setParameter("x", "4b81a971-5407-491f-b707-b49fab327372")
            .getResultStream().findAny().orElseGet({
                forma = new FormaPagamento()
                em.persist(forma)
                forma.descricao = "Cartão de Crédito à vista"
                forma.taxa = 3.19g
                forma.uuid = "4b81a971-5407-491f-b707-b49fab327372"
            })

        query.setParameter("x", "fd4f5d3b-7847-40b7-834e-a50711de0727")
            .getResultStream().findAny().orElseGet({
                forma = new FormaPagamento()
                em.persist(forma)
                forma.descricao = "Cartão de Crédito parcelado"
                forma.taxa = 3.79g
                forma.taxaMensal = 2.99g
                forma.uuid = "fd4f5d3b-7847-40b7-834e-a50711de0727"
                forma.parcelavel = true
            })
        
        query.setParameter("x", "b3bbf1ae-07a1-431f-8de0-584d82975fc9")
            .getResultStream().findAny().orElseGet({
                forma = new FormaPagamento()
                em.persist(forma)
                forma.descricao = "Cartão de Débito"
                forma.uuid = "b3bbf1ae-07a1-431f-8de0-584d82975fc9"
                forma.taxa = 1.99g
            })

        query.setParameter("x", "b73d27f3-7354-406d-86f0-d886cb4a698d")
            .getResultStream().findAny().orElseGet({
                forma = new FormaPagamento()
                em.persist(forma)
                forma.descricao = "Dinheiro"
                forma.uuid = "b73d27f3-7354-406d-86f0-d886cb4a698d"
            })

        
        query.setParameter("x", "80b9342d-e0ab-4151-952a-74022dc6a265")
            .getResultStream().findAny().orElseGet({
                forma = new FormaPagamento()
                em.persist(forma)
                forma.descricao = "Cheque"
                forma.uuid = "80b9342d-e0ab-4151-952a-74022dc6a265"
                forma.taxa = 1.99g
                forma.parcelavel = true
            })
        
        query.setParameter("x", "1fd4fb5c-2d46-4a8e-8955-a7aa05a7b18b")
            .getResultStream().findAny().orElseGet({
                forma = new FormaPagamento()
                em.persist(forma)
                forma.descricao = "Deposito"
                forma.uuid = "1fd4fb5c-2d46-4a8e-8955-a7aa05a7b18b"
                forma.taxa = 1.99g
                forma.parcelavel = true
            })
        
        query.setParameter("x", "3cb248de-e7f1-4da9-9fad-24ffecbdfe00")
            .getResultStream().findAny().orElseGet({
                forma = new FormaPagamento()
                em.persist(forma)
                forma.descricao = "Crediário"
                forma.uuid = "3cb248de-e7f1-4da9-9fad-24ffecbdfe00"
                forma.parcelavel = true
            })
        
        //--------------------Vendedor-------------------------
        em.createQuery("SELECT f FROM Vendedor f WHERE f.uuid = :x AND f.deleted = FALSE")
            .setParameter("x", "8bb34b92-b50e-4b58-934d-fa36e47dbf9a")
            .getResultStream().findAny().orElseGet({
                Vendedor admin = new Vendedor()
                em.persist(admin)
                admin.uuid = "8bb34b92-b50e-4b58-934d-fa36e47dbf9a"
                admin.nome = "Administrador"
            })
        
        
        em.transaction.commit();
        em.close();
    }
}
