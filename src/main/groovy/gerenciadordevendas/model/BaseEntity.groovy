/*
 * GerenciadorDeVendas: BaseEntity1.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 15:49:38
 */

package gerenciadordevendas.model

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.eclipse.persistence.annotations.AdditionalCriteria;

/**
 *
 * @author Ramon Porto
 */
@MappedSuperclass
@AdditionalCriteria("this.deleted = FALSE")
class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id
//    @Column(insertable = true, updatable = true)
    String uuid
    boolean deleted
    @Column(insertable = true, updatable = false)
    Timestamp dataCriacao
    @Column(insertable = false, updatable = true)
    Timestamp dataAtualizacao


    protected void setId(int id) {
        this.id = id
    }

    protected void setUuid(String uuid) {
        this.uuid = uuid
    }

    void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao
    }

    protected void setDataAtualizacao(Timestamp dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao
    }

    @PrePersist
    protected void onCreate() {
        setDataCriacao(new Timestamp(new Date().getTime()))
        setUuid(UUID.randomUUID().toString())
    }

    @PreUpdate
    protected void onPersist() {
        setDataAtualizacao(new Timestamp(new Date().getTime()))
    }

    @Override
    int hashCode() {
        int hash = 5
        hash = 47 * hash + Objects.hashCode(this.uuid)
        return hash
    }

    @Override
    boolean equals(Object obj) {
        if (obj == null) {
            return false
        }
        if (getClass() != obj.getClass()) {
            return false
        }
        final BaseEntity other = (BaseEntity) obj
        return Objects.equals(this.uuid, other.uuid)
    }

}


