/*
 * GerenciadorDeVendas: OperadorTableModel.java
 * Enconding: UTF-8
 * Data de criação: 09/02/2018 09:52:53
 */
package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.BaseEntity
import gerenciadordevendas.model.EntidadeSomenteComNome

import javax.persistence.Query
import java.awt.Window;
import java.lang.reflect.ParameterizedType
import javax.persistence.EntityManager
import javax.swing.JOptionPane;
import javax.swing.JTable

/**
 *
 * @author Ramon Porto
 */
class ItemNomeavelTableModel extends AbstractTableModelPesquisavel<EntidadeSomenteComNome> {


    private Class<?> classe
    private EntidadeSomenteComNome entidadeSelecionada
    
    ItemNomeavelTableModel(EntidadeSomenteComNome entidadeSelecionada, Class<?> classe) {
        super(['id', 'Nome'])
        this.entidadeSelecionada = entidadeSelecionada;
        this.classe = classe
        carregar()
    }

    @Override
    void atualizaEspacamentoColunas() {
        setJTableColumnsWidth(10, 90)
    }

    @Override
    protected Query getQuery(EntityManager em) {
        if (classe) {
            return em.createQuery("Select e from "+ classe.simpleName + " e")
        }
    }

    @Override
    protected boolean getSeachFilter(EntidadeSomenteComNome entidade, String campoLowerCase) {
        return entidade.nome.toLowerCase().contains(campoLowerCase)
    }

    @Override
    void novo(Window parent) {
        String nome = JOptionPane.showInputDialog(null, "Insira o nome");
        if (nome == null || nome.trim().empty || nome == "Insira o nome") {
            return;
        }
        def nomeavel = classe.newInstance()
        nomeavel.nome = nome
        
        EntityManager em = JPA.getEM();
        boolean naoContem = em.createQuery("Select e from "+ classe.simpleName + " e WHERE e.nome = :nome")
            .setParameter("nome", nome).resultList.empty
        if (naoContem) {
            em.transaction.begin();
            
            this.entidadeSelecionada = em.merge(nomeavel)
            em.transaction.commit()
            em.close()
            
            carregar()
        } else {
            JOptionPane.showMessageDialog(null, "Esse nome já existe");
        }
    }

    @Override
    void editar(Window parent) {
        int row = getJTable().selectedRow;
        if (row > -1) {
            EntidadeSomenteComNome selecionada = get(row);
            EntityManager em = JPA.getEM();
            def entidade = em.createQuery("Select e from "+ classe.simpleName + " e WHERE e.nome = :nome")
            .setParameter("nome", selecionada.nome).getSingleResult();
            
            String novoNome = JOptionPane.showInputDialog(null, entidade.nome);
            if (novoNome == null) {
                return;
            }
            boolean naoContem = em.createQuery("Select e from "+ classe.simpleName + " e WHERE e.nome = :nome")
            .setParameter("nome", novoNome).resultList.empty;
            if (naoContem) {
                entidade.nome = novoNome;
                em.transaction.begin();
                
                this.entidadeSelecionada = em.merge(entidade);
                em.transaction.commit();
                em.close();
                
                carregar();
            } else {
                JOptionPane.showMessageDialog(null, "Esse nome já existe");
            }
        }
    }
    
    void remover(java.awt.Window parent) {
        int row = getJTable().selectedRow;
        if (row > -1) {
            EntidadeSomenteComNome selecionada = get(row);
            EntityManager em = JPA.getEM();
            def entidade = em.createQuery("Select e from "+ classe.getSimpleName() + " e WHERE e.nome = :nome")
            .setParameter("nome", selecionada.nome).singleResult;
            entidade.deleted = true;
            
            em.transaction.begin();
            
            this.entidadeSelecionada = em.merge(entidade);
            em.transaction.commit();
            em.close();
            
            carregar();
        }
    }


    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        EntidadeSomenteComNome entidade = get(rowIndex);
        switch (colunas[columnIndex]) {
            case 'id':    return entidade.id
            case 'Nome':  return entidade.nome
        }
        return null;
    }
}
