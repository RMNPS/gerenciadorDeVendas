/*
 * GerenciadorDeVendas: JPA1.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 16:58:32
 */

package gerenciadordevendas

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;

/**
 *
 * @author ramon
 */
class JPA {

    static EntityManagerFactory emf;

    static EntityManager getEM() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(
                    "GerenciadorPU");
        }
        return emf.createEntityManager();
    }

    /** Com o autoMerge não é necessário abrir e fechar o EntityManager, nem iniciar a transação,
     * pois ele faz automático.
     * @param <T>
     * @param ma Inteface para aplicar o merge.
     * @return 
     */
    static <T> T autoMerge(MergingAction<T> ma) {
        EntityManager em = JPA.getEM();
        em.transaction.begin();
        T result = ma.apply(em);
        em.transaction.commit();
        em.close();
        
        return result;
    }
    

}


