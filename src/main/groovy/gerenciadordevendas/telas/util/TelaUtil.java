/*
 * GerenciadorDeVendas: TelaUtil.java
 * Enconding: UTF-8
 * Data de criação: 25/03/2019 11:51:27
 */
package gerenciadordevendas.telas.util;

import gerenciadordevendas.EntityService;
import gerenciadordevendas.model.Empresa;
import gerenciadordevendas.model.TipoEmpresa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JComboBox;

/**
 *
 * @author Ramon Porto
 */
public class TelaUtil {

    private TelaUtil() {
    }  
    
    public static <T extends Comparable<T>> void carregarObjetosNaComboBox(EntityManager em, JComboBox<T> cmb, Class<T> classe) {
        cmb.removeAllItems();

        List<T> objetos = EntityService.getList(em, classe);
        cmb.insertItemAt(null, 0);
        for (T objeto : objetos) {
            cmb.addItem(objeto);
        }
    }
    
    public static  void carregarEmpresaNaComboBox(EntityManager em, JComboBox<Empresa> cmb, TipoEmpresa tipo) {
        cmb.removeAllItems();
        
        List<Empresa> objetos = em.createQuery("SELECT e FROM Empresa e WHERE e.tipoEmpresa = :tipoEmpresa")
                .setParameter("tipoEmpresa", tipo)
                .getResultList();
        cmb.insertItemAt(null, 0);
        for (Empresa objeto : objetos) {
            cmb.addItem(objeto);
        }
    }
}
