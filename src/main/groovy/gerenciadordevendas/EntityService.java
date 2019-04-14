/*
 * GerenciadorDeVendas: EntityService.java
 * Enconding: UTF-8
 * Data de criação: 22/03/2019 14:10:35
 */
package gerenciadordevendas;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Ramon Porto
 */
public class EntityService {

    public static <T> List<T> getList(EntityManager em, Class<T> classe) {
        return em
                .createQuery("SELECT e FROM " + classe.getSimpleName() + " e WHERE e.deleted = FALSE", classe)
                .getResultList();
    }

   public static String getLastIDplus1(EntityManager em, Class<?> classe) {
        List<Integer> lista = em.createQuery("SELECT e.id FROM "+classe.getSimpleName()+" e order by e.id desc").setMaxResults(1).getResultList();
        String id = "";
        if (!lista.isEmpty()) {
            id = ""+ (lista.get(0) + 1);
        }
        return id;
    } 
    
}
