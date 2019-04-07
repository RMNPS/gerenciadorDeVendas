package gerenciadordevendas.model;

import gerenciadordevendas.JPA;
import javax.persistence.EntityManager;

@javax.persistence.Entity
public class Vendedor extends BaseEntity implements EntidadeSomenteComNome, Comparable<Vendedor>{
    private static Vendedor PADRAO;
    private String nome;

    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static Vendedor padrao() {
        if (PADRAO == null) {
            EntityManager em = JPA.getEM();
            PADRAO =  em.createQuery("SELECT f FROM Vendedor f WHERE f.uuid = :x AND f.deleted = FALSE", Vendedor.class)
            .setParameter("x", "8bb34b92-b50e-4b58-934d-fa36e47dbf9a")
            .getResultStream().findAny().orElseGet(() ->{
                Vendedor admin = new Vendedor();
                admin.setUuid("8bb34b92-b50e-4b58-934d-fa36e47dbf9a");
                admin.setNome("Administrador");
                return em.merge(admin);
            });
            em.close();
        }
        return PADRAO;
    }

    @Override
    public String toString() {
        return  nome ;
    }
    
    @Override
    public int compareTo(Vendedor o) { return nome.compareTo(o.nome); };
}
