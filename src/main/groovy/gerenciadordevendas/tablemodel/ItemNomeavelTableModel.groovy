/*
 * GerenciadorDeVendas: OperadorTableModel.java
 * Enconding: UTF-8
 * Data de criação: 09/02/2018 09:52:53
 */
package gerenciadordevendas.tablemodel;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.BaseEntity
import gerenciadordevendas.model.EntidadeSomenteComNome
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

    private Class<?> classe;
    private EntidadeSomenteComNome entidadeSelecionada;
    
    public ItemNomeavelTableModel(EntidadeSomenteComNome entidadeSelecionada, Class<?> classe) {
        this.entidadeSelecionada = entidadeSelecionada;
        this.classe = classe
        carregar();
    }

    @Override
    final void setJTable(JTable table) {
        super.table = table;
        setJTableColumnsWidth(800, 20, 80);
    }
    
    @Override
    protected String getJPQL() {
        return "Select e from "+ classe.getSimpleName() + " e WHERE e.deleted = FALSE";
    }

    @Override
    protected boolean getSeachFilter(EntidadeSomenteComNome entidade, String campoLowerCase) {
        return entidade.nome.toLowerCase().contains(campoLowerCase);
    }

    @Override
    void novo(Window parent) {
        String nome = JOptionPane.showInputDialog(null, "Insira o nome");
        if (nome == null || nome.trim().isEmpty() || nome.equals("Insira o nome")) {
            return;
        }
        def nomeavel = classe.newInstance()
        nomeavel.nome = nome;
        
        EntityManager em = JPA.getEM();
        boolean naoContem = em.createQuery("Select e from "+ classe.getSimpleName() + " e WHERE e.deleted = FALSE and e.nome = :nome")
            .setParameter("nome", nome).getResultList().isEmpty();
        if (naoContem) {
            em.getTransaction().begin();
            
            this.entidadeSelecionada = em.merge(nomeavel);
            em.getTransaction().commit();
            em.close();
            
            carregar();
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
            def entidade = em.createQuery("Select e from "+ classe.getSimpleName() + " e WHERE e.deleted = FALSE and e.nome = :nome")
            .setParameter("nome", selecionada.nome).getSingleResult();
            
            String novoNome = JOptionPane.showInputDialog(null, entidade.nome);
            if (novoNome == null) {
                return;
            }
            boolean naoContem = em.createQuery("Select e from "+ classe.getSimpleName() + " e WHERE e.deleted = FALSE and e.nome = :nome")
            .setParameter("nome", novoNome).getResultList().isEmpty();
            if (naoContem) {
                entidade.nome = novoNome;
                em.getTransaction().begin();
                
                this.entidadeSelecionada = em.merge(entidade);
                em.getTransaction().commit();
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
            def entidade = em.createQuery("Select e from "+ classe.getSimpleName() + " e WHERE e.deleted = FALSE and e.nome = :nome")
            .setParameter("nome", selecionada.nome).getSingleResult();
            entidade.deleted = true;
            
            em.getTransaction().begin();
            
            this.entidadeSelecionada = em.merge(entidade);
            em.getTransaction().commit();
            em.close();
            
            carregar();
        }
    }

    enum Colunas {
        ID("id"), NOME("Nome");

        private String nome;

        private Colunas(String nome) {
            this.nome = nome;
        }
    }

    @Override
    int getColumnCount() { Colunas.values().length }

    @Override
    String getColumnName(int column) {
        return Colunas.values()[column].nome;
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        EntidadeSomenteComNome entidade = get(rowIndex);
        switch (Colunas.values()[columnIndex]) {
            case Colunas.ID:    return entidade.id
            case Colunas.NOME:  return entidade.nome
        }
        return null;
    }
}
