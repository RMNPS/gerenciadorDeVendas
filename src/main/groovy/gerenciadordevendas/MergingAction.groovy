/*
 * GerenciadorDeVendas: SavingAction.java
 * Enconding: UTF-8
 * Data de criação: 22/05/2018 17:02:06
 */
package gerenciadordevendas;

import javax.persistence.EntityManager;

/**
 *
 * @author Ramon Porto
 * @param <T>
 */
public interface MergingAction<T> {
    
    T apply (EntityManager em);
}
